package com.agsword.asbi.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.yml中的配置
 * @author as
 */
@Component
public class FileUtils implements InitializingBean {

    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String REGION;
    public static String BUCKET;
    @Value("${cos.client.accessKey}")
    private String accessKey;
    @Value("${cos.client.secretKey}")
    private String secretKey;
    @Value("${cos.client.region}")
    private String region;
    @Value("${cos.client.bucket}")
    private String bucket;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY = this.accessKey;
        SECRET_KEY = this.secretKey;
        REGION = this.region;
        BUCKET = this.bucket;
    }
}