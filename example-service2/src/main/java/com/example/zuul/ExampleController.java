package com.example.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/example")
public class ExampleController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/get")
    public String get(HttpServletRequest request){
        return "this is example service 2 " + port;
    }
}
