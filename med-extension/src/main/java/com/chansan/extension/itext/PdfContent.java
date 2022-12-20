package com.chansan.extension.itext;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author yf
 */
@Data
public  class PdfContent implements Serializable {

    private static final long serialVersionUID = -3803771687522847376L;

    private String title;
    private String fileName;
    private List<List<Item>> pages;

    public File genTempFile(String path){
        return new File(String.format("%s/%s.pdf", path, fileName));
    };


    @Data
    @Accessors(chain = true)
    public static class  Item implements Serializable{
        private String label;
        private String val;

        public boolean isImg(String val){
            return true;
        }

    }


}