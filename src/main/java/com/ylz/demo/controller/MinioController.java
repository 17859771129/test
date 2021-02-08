package com.ylz.demo.controller;

import com.google.common.io.CharStreams;
import com.ylz.demo.service.IFileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@RestController
public class MinioController {
    @Autowired
    private IFileSystemService iFileSystemService;
    @RequestMapping("/upload")
    public String uploadFile(String stream, String descPath){
        return iFileSystemService.uploadStringToFile(stream,descPath);
    }
    @RequestMapping("/download")
    public String downloadFile(String fullPath) throws IOException {
        return iFileSystemService.getFielToString(fullPath);
    }
    @RequestMapping("/isExists")
    public boolean isExists(String filePath){
        return iFileSystemService.isExists(filePath);
    }
    @RequestMapping("/getInputStream")
    public String getInputStream(String path, String charset) throws IOException {
        InputStream inputStream = null;
        inputStream = iFileSystemService.getInputStream(path, charset);
        return CharStreams.toString(new
                InputStreamReader(inputStream, "UTF-8"));
    }

}
