package com.ylz.demo.service.imp;

import com.ylz.demo.service.IFileSystemService;
import com.ylz.demo.service.IMinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service("minioFileSystemService")
public class MinioFileSystemServiceImpl implements IFileSystemService {
    private static final Logger log = LoggerFactory.getLogger(MinioFileSystemServiceImpl.class);

    @Autowired
    private IMinioService minioService;
    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public String getCode() {
        return "MINIO";
    }

    @Override
    public String uploadStringToFile(String stream, String descPath) {
        return minioService.uploadStingTo(bucket,descPath,stream);
    }


    @Override
    public InputStream getInputStream(String path, String charset)  {
        return minioService.getInputStream(bucket,path);
    }

    @Override
    public String getFielToString(String fullPath) {
        String file = minioService.downloadMinio(bucket,fullPath);
        return file;

    }

    @Override
    public boolean isExists(String filePath) {
        minioService.uploadFileMinio_Source_Exist(bucket,filePath);
        return false;
    }


}
