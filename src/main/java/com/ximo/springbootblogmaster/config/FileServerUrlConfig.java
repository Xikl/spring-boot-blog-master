package com.ximo.springbootblogmaster.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱文赵
 * @date 2018/5/17
 * @description 文件服务器地址
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file.server")
public class FileServerUrlConfig {

    /** 文件服务器地址 */
    private String url;

}
