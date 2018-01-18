package com.example.zuul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/get")
    public String get(){
        return "this is example service " + port;
    }
}
