package com.example.DemoProject.controller;

import com.example.DemoProject.dto.ResponseDTO;
import com.example.DemoProject.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")//리소스
public class TestController {

    @GetMapping
    public String testController() {
         return "Hello world! testGetMapping";
    }

     @GetMapping("/testGetMapping")
     public String testControllerWithPath(){
          return "Hello World! testGetMappingWithPath!";
     }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id){
        return "Hello World! ID "+ id;
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id){
        return "Hello World! ID"+id;
    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseDTO(){
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    //RequestEntity
    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("Hello world! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        //http status -> 400
        return ResponseEntity.badRequest().body(response);
    }

}
