package com.chansan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @name: $NAME
 * @author£º yf
 * @classPath: com.chansan.$NAME
 * @date: $DATE
 * @Version: 1.0
 * @description: ÎÄ¼þÃèÊö:
 */
@SpringBootApplication
public class RunApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
        System.out.println("Hello world!");
    }
}