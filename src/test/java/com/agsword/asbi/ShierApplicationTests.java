package com.agsword.asbi;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

/**
 * 主类测试
 *
 */
@SpringBootTest
class asApplicationTests {
    public static void main(String[] args) {
        String version = SpringVersion.getVersion();
        System.out.println("SpringVersion="+version);
    }
}
