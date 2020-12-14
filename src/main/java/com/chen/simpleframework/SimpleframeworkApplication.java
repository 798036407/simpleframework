package com.chen.simpleframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SimpleframeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleframeworkApplication.class, args);
    }

}
