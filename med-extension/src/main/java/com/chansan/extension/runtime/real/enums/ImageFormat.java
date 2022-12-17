package com.chansan.extension.runtime.real.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @name: ImageFormat
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.enums.ImageFormat
 * @date: 2022/12/17
 * @description: 图片格式
 */
@Getter
@AllArgsConstructor
public enum ImageFormat {
    /**
     *
     */
    JPG("jpg"),
    PNG("png"),
    WEBP("webp");
    private final String format;

}
