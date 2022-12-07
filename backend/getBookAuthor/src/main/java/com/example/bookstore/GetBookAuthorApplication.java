package com.example.bookstore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;
//
@RestController
@SpringBootApplication
@ServletComponentScan
public class GetBookAuthorApplication {


    public static void main(String[] args) {
        SpringApplication.run(GetBookAuthorApplication.class, args);
    }



}
