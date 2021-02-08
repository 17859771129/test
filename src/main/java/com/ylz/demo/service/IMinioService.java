package com.ylz.demo.service;

import java.io.File;
import java.io.InputStream;

public interface IMinioService {

    /**
     * 上传文件到minio
     *
     * @return
     * @Param bucketName  桶名称
     * @Param fileName  文件名
     */
    String uploadFileMinio(String bucketName, File sourceFile, String descPath);


    /**
     * 上传本地文件到minio
     * @param bucketName
     * @param sourcePath
     * @param descPath
     * @return
     */
    String uploadLoaclFileToMinio(String bucketName, String sourcePath, String descPath);


    /**
     *
     * @param bucketName 桶名称
     * @param filePath 文件路径
     * @param stream 上传字符串
     * @return
     */
    String uploadStingTo(String bucketName, String filePath, String stream);

    /**
     * 上传文件是否存在    http://192.168.31.89:9000/minio/ssss/
     *
     * @return
     * @Param bucketName  桶名称
     * @Param stream   文件内容
     */
    String uploadFileMinio_Source_Exist(String bucketName, String filePath);

    /**
     * 文件是否存在
     * @param bucketName
     * @param filePath
     * @return
     */
    Boolean isExist(String bucketName, String filePath);

    /**
     * 下载
     *
     * @return
     * @Param bucketName  桶名称
     * @Param fileName  文件名
     */
    String downloadMinio(String bucketName, String fileName);


    InputStream getInputStream(String bucketName, String fileName);


}
