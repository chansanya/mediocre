package com.chansan.extension.itext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

/**
 * @name: PdfUtil2Test
 * @author: leihuangyan
 * @classPath: com.chansan.extension.itext.PdfUtil2Test
 * @date: 2022/12/20
 * @description:
 */
class PdfUtil2Test {

    @Test
    public void run() {

        PdfContent content = new PdfContent();
        content.setFileName("生成报告");
        content.setTitle("个人信息");

        List<PdfContent.Item> parse2 = PdfUtil2.parse(Consts.TEST_JSON, Consts.TEST_CONFIG);
        List<PdfContent.Item> parse3 = PdfUtil2.parse(Consts.TEST_JSON, Consts.TEST_CONFIG);

        List<List<PdfContent.Item>> pages = new ArrayList<>();
        pages.add(parse2);
        pages.add(parse3);
        content.setPages(pages);

        PdfUtil2.genPDF(content, i -> {
            System.out.printf("开始文件上传:%s", i.getPath());
            System.out.println();
//            boolean deleteFile = i.delete();
//            log.info("删除临时文件:{}",deleteFile);
        });
    }

    private final  static String SOURCE_FILE = "E:\\Users\\yf\\Downloads\\pdf\\test.pdf";
    private final  static String TAGGER_FILE = "E:\\Users\\yf\\Downloads\\pdf\\tagger-pk12.pdf";
    private final  static String YZ_FILE = "E:\\Users\\yf\\Downloads\\pdf\\yz.png";

    public static final String KEYSTORE = "E:\\Users\\yf\\Downloads\\pdf\\test.keystore";
    //    public static final String KEYSTORE_P12 = "E:\\Users\\yf\\Downloads\\pdf\\test.p12";
//    public static final char[] PASSWORD = "123456".toCharArray();
    public static final String KEYSTORE_P12 = "E:\\Users\\yf\\Downloads\\pdf\\abc.pfx";
    public static final String PASSWORD = "miss3331963";


    //keytool -list  -v -keystore e:/Users/yf/Downloads/pdf/test.keystore -storepass 123456
    //keytool -genkeypair -alias stamper -keypass 123456 -keyalg RSA -keysize 1024 -validity 365 -keystore e:/Users/yf/Downloads/pdf/test.keystore -storepass 123456
    //keytool -importkeystore -srckeystore e:/Users/yf/Downloads/pdf/test.keystore -destkeystore e:/Users/yf/Downloads/pdf/test.p12  -deststoretype pkcs12


    @Test
    public void sign() throws IOException {
        PdfSignUtil.SingModel pkcs12 = PdfSignUtil.SingModel.builder()
                .sourceFileStream(PdfSignUtil.getFileInputStream(SOURCE_FILE))
                .targetFileStream(PdfSignUtil.getFileOutStream(TAGGER_FILE))
                .certStream(PdfSignUtil.getFileInputStream(KEYSTORE_P12))
                .x(595-100).y(842-100).height(50).width(50)
                .pageNumber(1).signAllPge(false)
                .keyStoreType(PdfSignUtil.KeyStoreType.PKCS12)
                .password(PASSWORD)
                .signImgByte(PdfSignUtil.getFileBytes(YZ_FILE))
                .tsaClient("http://timestamp.comodoca.com/rfc3161")
                .reason("签署一个PDF")
                .location("小彦测试签署")
                .build();


        PdfDocument document = new PdfDocument(new PdfReader((PdfSignUtil.getFileInputStream(SOURCE_FILE))));
        Rectangle pageSize = document.getPage(1).getPageSize();
        float height = pageSize.getHeight();
        float width = pageSize.getWidth();
        System.out.println(height);
        System.out.println(width);
        document.close();

        PdfSignUtil.sign(pkcs12);

    }


    @Test
    public void watermark() throws Exception {
        File file = new File("E:\\Users\\yf\\Downloads\\pdf\\tagger.pdf");
        System.out.println(file.exists());

        PdfUtil2.watermark(PdfUtil2.WatermarkModel.builder()
                .sourceFileStream(PdfSignUtil.getFileInputStream(SOURCE_FILE))
                .targetFileStream(PdfSignUtil.getFileOutStream("E:\\Users\\yf\\Downloads\\pdf\\tagger.pdf"))
                .content("有方信息")
                .fontSize(60)
                .rotate(180)
                .pageNumber(1)
                .center(true)
                .x(595-100).y(842-100)
//                .height(50).width(50)
                .build());
    }

}