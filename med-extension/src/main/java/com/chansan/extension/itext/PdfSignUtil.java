package com.chansan.extension.itext;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.PdfSignatureAppearance;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;
import com.itextpdf.signatures.TSAClientBouncyCastle;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PdfSignUtil {

    enum KeyStoreType{
        /**
         *
         */
        PKCS12,JKS
    }

    @Data
    @Builder
    static  class SingModel{

        /**
         * 源文件路径
         */
        private InputStream  sourceFileStream;
        private OutputStream targetFileStream;

        private String  reason;
        private String  location;

        private float  x;
        private float  y;

        private float  height;
        private float  width;

        private boolean  signAllPge;
        private Integer  pageNumber;
        private KeyStoreType keyStoreType;
        private InputStream  certStream;
        private String  password;
        private byte[] signImgByte;
        /**
         * <a href="https://blog.csdn.net/qq_43615820/article/details/125679638">...</a>
         */
        private String tsaClient;
    }


    /**
     * 签名
     * @param model  SingModel
     */
    public static void sign(SingModel model)  {

        try {
            //读取源文件
            PdfReader pdfReader = new PdfReader(model.getSourceFileStream());
            //构建 PdfSigner 写入目标文件
            PdfSigner pdfSigner = new  PdfSigner(pdfReader,model.getTargetFileStream(), new StampingProperties());




            //设置签名属性
            PdfSignatureAppearance appearance = pdfSigner.getSignatureAppearance();
            appearance.setReason(model.getReason());
            appearance.setLocation(model.getLocation());
            //设置签名页
            appearance.setPageNumber(model.getPageNumber());
            //设置签名矩形位置
            Rectangle rectangle = new Rectangle(model.getX(), model.getY(), model.getWidth(), model.getHeight());
            appearance.setPageRect(rectangle);
            //插入盖章图片
            ImageData imageData = ImageDataFactory.create(model.getSignImgByte());
            //旋转
            imageData.setRotation(180);
            appearance.setSignatureGraphic(imageData);
            //设置图章的显示方式，如下选择的是只显示图章（还有其他的模式，可以图章和签名描述一同显示）
            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);


            //提供加密
            Security.addProvider(new BouncyCastleProvider());

            //秘钥库
            KeyStore ks = geKeyStore(model.getKeyStoreType().name(), model.getCertStream(), model.getPassword());
            String alias = ks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) ks.getKey(alias, model.getPassword().toCharArray());

            // 设置级别,作者签名，不允许修改。
            pdfSigner.setCertificationLevel(PdfSigner.CERTIFIED_NO_CHANGES_ALLOWED);

            //签名
            pdfSigner.signDetached(
                    //摘要算法
                    new BouncyCastleDigest(),
                    // 签名算法
                    new PrivateKeySignature(pk,  DigestAlgorithms.SHA256, BouncyCastleProvider.PROVIDER_NAME),
                    //证书链
                    ks.getCertificateChain(alias), null, null,
                    //时间戳服务器
                    new TSAClientBouncyCastle(model.getTsaClient()),
                    0, PdfSigner.CryptoStandard.CADES);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     *
     * @param type PKCS12 ||  JKS
     * @param certStream 证书文件流
     * @param certPassword 证书密码
     * @return 秘钥库
     */
    public static KeyStore geKeyStore(String type, InputStream certStream, String certPassword){
        try {
            KeyStore ks = KeyStore.getInstance(type);
            ks.load(certStream, certPassword.toCharArray());
            return ks;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }
    public static InputStream getFileInputStream(String filePath){
        try {
            return Files.newInputStream(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static OutputStream getFileOutStream(String filePath){
        try {
            return Files.newOutputStream(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte [] getFileBytes(String filePath){
        try {
            return IOUtils.toByteArray(getFileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static float getPdfX(float x){
        return 0;
    }

    public static float getPdfY(float x){
        return 0;
    }





}
