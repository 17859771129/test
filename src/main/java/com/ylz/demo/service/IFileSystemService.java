package com.ylz.demo.service;

import java.io.IOException;
import java.io.InputStream;

public interface IFileSystemService {

    /**
     * 查询交通方式编码
     *
     * @return 编码
     */
    String getCode();

//    String uploadToFile(InputStream inputStream, String filePath);

//    boolean uploadToFile(String sourcePath, String descPath);

    String uploadStringToFile(String string, String descPath) ;

    InputStream getInputStream(String path, String charset) throws IOException;

    String getFielToString(String fullPath) throws IOException;

    boolean isExists(String filePath);

//    File getFile(String path, boolean addPrefix);

//    String getBase64String(String filePath);

//    String uploadBase64ToFile(String base64String, String filePath);
}
