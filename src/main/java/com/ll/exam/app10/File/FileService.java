package com.ll.exam.app10.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
@Service
public class FileService {
    public void upload(MultipartFile file) throws FileNotFoundException {
        FileOutputStream fileOutputStream=new FileOutputStream("c:/upload/app10");
        try {
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
