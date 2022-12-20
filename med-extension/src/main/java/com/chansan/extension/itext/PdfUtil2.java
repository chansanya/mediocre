package com.chansan.extension.itext;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.chansan.extension.exception.PdfException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdfUtil2 {


    private static final String FONT_NAME = "/font/simhei.ttf";

    private static PdfFont FONT = null;



    public static List<PdfContent.Item>  parse(String valJson,String configJson){
        List<PdfContent.Item> list = new ArrayList<>();
        JsonObject conObg =  new Gson().fromJson(configJson,JsonObject.class);
        JsonObject valObg =  new Gson().fromJson(valJson,JsonObject.class);

        JsonArray fields =(JsonArray) conObg.get("fields");
        for (JsonElement field : fields) {
            JsonObject fieldObg = field.getAsJsonObject();
            String val = valObg.get(fieldObg.get("__vModel__").getAsString()).getAsString();
            if(null == val){
                continue;
            }

            PdfContent.Item item = new PdfContent.Item();
            JsonObject fieldConf = fieldObg.getAsJsonObject("__config__");
            String label = fieldConf.get("label").getAsString();
            item.setLabel(label);
            item.setVal(val);

            list.add(item);
        }


        return list;
    }

    public static void genPDF(PdfContent pdf) {
        genPDF(pdf, i->{});
    }

    public static void genPDF(PdfContent pdf, Consumer<File> fileHandel) {

        File tempFile = pdf.genTempFile(String.format("%s/uploadPath", System.getProperty("user.home")));
        log.info("生成临时文件路径:{}",tempFile);

        try (OutputStream outputStream = Files.newOutputStream(tempFile.toPath())) {
            PdfWriter pdfWriter = new PdfWriter(outputStream);

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);
            initFont();

            //头部分
            Paragraph titlePar = createParagraph(pdf.getTitle(), 28);
            titlePar.setTextAlignment(TextAlignment.CENTER);
            document.add(titlePar);

            Iterator<List<PdfContent.Item>> iterator = pdf.getPages().iterator();

            while (iterator.hasNext()){
                List<PdfContent.Item> items = iterator.next();

                Table table = new Table(2);
                table.useAllAvailableWidth();
                table.setMarginTop(4);

                Cell cell = new Cell(1, 1);
                cell.setBorder(Border.NO_BORDER);
                //居右
                cell.setTextAlignment(TextAlignment.RIGHT);
                //增加内容
                String format = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
                cell.add(new Paragraph(format));
                //添加单元格到表格
                table.addCell(cell);

                Border border = new SolidBorder(ColorConstants.BLACK, 2.0f);
                //表格底边框 红色
                table.setBorderBottom(border);
                //将表格添加到文档
                document.add(table);

                document.add(new Paragraph());
                document.add(new Paragraph());

                Div div = new Div();
                div.setMargin(10);

                for (PdfContent.Item item : items) {
                    String val = item.getVal();
                    Paragraph label = createLabel(item.getLabel()).setMarginLeft(20);
                    div.add(label);

                    if (val.startsWith("http")  || val.startsWith("https")  ) {
                        Image image = new Image(ImageDataFactory.create(new URL(val))).setMarginLeft(20);
                        div.add(image);
                    } else {
                        label.add(createParagraph(val, 15).setMarginLeft(3));
                    }
                }
                document.add(div);

                if(iterator.hasNext()){
                    //转到下一页
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
            }

            watermark(pdfDocument, document);
            document.close();

            fileHandel.accept(tempFile);
        } catch (IOException e) {
            log.error("保存pdf文件异常",e);
            throw new PdfException(e.getMessage());
        }
    }

    private static Paragraph createLabel(String val) {
        return createParagraph(String.format("%s:", val), 17);
    }

    private static Paragraph createParagraph(String val, int size) {
        return new Paragraph(val).setFont(FONT).setFontSize(size);
    }

    private static void initFont() {
        try {
            FONT = PdfFontFactory.createFont( IOUtils.resourceToByteArray(FONT_NAME), PdfEncodings.IDENTITY_H);
        } catch (IOException e) {
            throw new PdfException(String.format("获取中文字体:%s失败",FONT_NAME));
        }
    }



    @Data
    @Builder
    static
    class  WatermarkModel{

        private OutputStream targetFileStream;
        private InputStream sourceFileStream;


        private float  x;
        private float  y;

        private float  height;
        private float  width;

        private String   content;
        private Integer  pageNumber;
        private Integer  rotate;
        private Integer  fontSize;

        private boolean  center;

    }

    public static void watermark(WatermarkModel watermarkModel) throws Exception{
//        OutputStream targetFileStream = watermarkModel.getTargetFileStream();
//        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(targetFileStream));
        PdfDocument readPdfDocument = new PdfDocument(new PdfReader(watermarkModel.getSourceFileStream()));


        PdfDocument writePdfDocument = new PdfDocument(new PdfWriter(watermarkModel.getTargetFileStream()));
        Document  writeDocument = new Document(writePdfDocument);

        int numberOfPdfObjects = readPdfDocument.getNumberOfPages();
        log.info("PDF页数:{}",numberOfPdfObjects);

        if(watermarkModel.isCenter()){
            extracted(watermarkModel,writeDocument,watermarkModel.getPageNumber(),readPdfDocument);
            return;
        }
        for (int i = 1; i <= numberOfPdfObjects; i++) {
            extracted(watermarkModel, writeDocument,i, readPdfDocument);
        }

    }

    private static void extracted(WatermarkModel model,Document document,int index, PdfDocument pdfDocument) {
        PdfPage page = pdfDocument.getPage(index);
        if(null== page){
            return;
        }
        PdfCanvas canvas = new PdfCanvas(page);
        canvas.saveState();
        //透明度
        PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.2f);
        canvas.setExtGState(gs1);

        Rectangle pageSize = page.getPageSize();
        float height = pageSize.getHeight();
        float width = pageSize.getWidth();

        System.out.println("pdf 宽:"+width);
        System.out.println("pdf 高:"+height);
//            float left = pageSize.getLeft();
//            float right = pageSize.getRight();
//            float top = pageSize.getTop();
//            float bottom = pageSize.getBottom();
        //水印内容
        Paragraph watermarkContent = new Paragraph(model.getContent())
                .setFont(FONT)
                .setFontSize(model.getFontSize());

        document.showTextAligned(watermarkContent,
                model.getX(),
                model.getY(),
                pdfDocument.getPageNumber(page),
                TextAlignment.CENTER, VerticalAlignment.MIDDLE,
                model.getRotate()
        );
    }




    public static void watermark(PdfDocument pdfDocument, Document document) {
        int numberOfPdfObjects = pdfDocument.getNumberOfPages();
        log.info("PDF页数:{}",numberOfPdfObjects);
        for (int i = 1; i <= numberOfPdfObjects; i++) {
            PdfPage page = pdfDocument.getPage(i);
            if(null== page){
                continue;
            }
            PdfCanvas canvas = new PdfCanvas(page);
            canvas.saveState();
            //透明度
            PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.2f);
            canvas.setExtGState(gs1);

            Rectangle pageSize = page.getPageSize();
            float height = pageSize.getHeight();
            float width = pageSize.getWidth();
            System.out.println("pdf 宽:"+width);
            System.out.println("pdf 高:"+height);
//            float left = pageSize.getLeft();
//            float right = pageSize.getRight();
//            float top = pageSize.getTop();
//            float bottom = pageSize.getBottom();
            //水印内容
            Paragraph watermarkContent = new Paragraph("有方电子签章").setFont(FONT).setFontSize(60);

            document.showTextAligned(watermarkContent, width / 2, height / 2, pdfDocument.getPageNumber(page), TextAlignment.CENTER, VerticalAlignment.MIDDLE, 45);

        }
    }


}
