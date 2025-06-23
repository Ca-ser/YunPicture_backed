package com.waiit.yun_picture_backed.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/main")
public class MainController {
    
    @GetMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "name",defaultValue = "World") String name) {
        return "Hello,"+name;
    }
}
