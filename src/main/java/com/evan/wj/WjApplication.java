package com.evan.wj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WjApplication {

    public static void main(String[] args) {
        SpringApplication.run(WjApplication.class, args);

        System.out.println("系统启动成功!");
    }

}
