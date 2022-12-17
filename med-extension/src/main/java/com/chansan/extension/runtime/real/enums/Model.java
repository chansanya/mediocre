package com.chansan.extension.runtime.real.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @name: Model
 * @author: leihuangyan
 * @classPath: com.chansan.extension.runtime.real.enums.Model
 * @date: 2022/12/17
 * @description: 模型
 * <a href="https://github.com/xinntao/Real-ESRGAN/blob/master/README_CN.md">...</a>
 */
@Getter
@AllArgsConstructor
public enum Model {
    /**
     * 默认
     */
    DEFAULT("realesrgan-x4plus"),
    /**
     *
     */
    NET("reaesrnet-x4plus"),
    /**
     * 针对动漫插画图像优化，有更小的体积
     */
    ANIME("realesrgan-x4plus-anime"),

    /**
     * 针对动漫视频
     */
    ANIME_VIDEO("realesr-animevideov3");

    private final String model;
}
