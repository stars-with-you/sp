package com.fl.sp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/test")
    public String Test(){
        return "test";
    }
}
