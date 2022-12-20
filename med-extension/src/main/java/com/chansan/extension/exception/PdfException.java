package com.chansan.extension.exception;

import java.text.MessageFormat;

/**
 * @name: RealException
 * @author: leihuangyan
 * @classPath: com.chansan.extension.exception.RealException
 * @date: 2022/12/19
 * @description:
 */
public class PdfException extends RuntimeException{

    public PdfException(String pattern, Object... objects ) {
        super(MessageFormat.format(pattern,objects));
    }

}
