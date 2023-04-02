package com.example.DemoProject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")//리소스
public class TestController {

    /*
    @GetMapping("/test/testGetMapping")
    public String testController(){
        return "Hello world! testGetMapping";
    }
    */
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id){
        return "Hello World! ID "+ id;
    }

}
