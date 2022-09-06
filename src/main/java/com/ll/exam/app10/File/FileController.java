package com.ll.exam.app10.File;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileController {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final FileService fileService;
    @GetMapping("/test/upload")
    public String testUpload(){
        return "test/upload";
    }

    @PostMapping("/test/upload")
    @ResponseBody
    public String upload(@RequestParam("img1") MultipartFile img1, @RequestParam("img2") MultipartFile img2) {
        try {
            img1.transferTo(new File(genFileDirPath + "/1.png"));
            img2.transferTo(new File(genFileDirPath + "/2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "업로드 완료!";
    }
}
