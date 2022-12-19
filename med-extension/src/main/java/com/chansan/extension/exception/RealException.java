package com.chansan.extension.exception;

import java.text.MessageFormat;

/**
 * @name: RealException
 * @author: leihuangyan
 * @classPath: com.chansan.extension.exception.RealException
 * @date: 2022/12/19
 * @description:
 */
public class RealException extends RuntimeException{

    public RealException(String pattern,Object... objects ) {
        super(MessageFormat.format(pattern,objects));
    }

}
