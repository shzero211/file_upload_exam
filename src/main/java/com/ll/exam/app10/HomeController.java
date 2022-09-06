package com.ll.exam.app10;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {
    @GetMapping("/")
    public String main(){
        return "home/main";
    }
}
