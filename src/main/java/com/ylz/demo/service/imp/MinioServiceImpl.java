package com.ylz.demo.service.imp;

import com.ylz.demo.service.IMinioService;
import com.ylz.demo.tool.FileFunctionUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
public class MinioServiceImpl implements IMinioService {
    private static final Logger log = LoggerFactory.getLogger(MinioServiceImpl.class);

    @Value("${minio.bucket}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     *
     * @return
     * @Param bucketName  桶名称
     * @Param fileName  文件名
     */
    @Override
    public String uploadFileMinio(String bucketName, File sourceFile, String descPath) {
        try {
            /** @Desc 创建桶   */
            /** @Desc 判断是否成功  */
            if (!createBucket(bucketName)) {
                log.info("创建桶失败，无法上传！" + bucketName);
                return null;
            }
            uploadFile(sourceFile, descPath);
            return descPath;
        } catch (Exception e) {
            log.info("文件上传失败，桶名称:{},源文件:{},目标路径为：{}，错误信息为：{}，{}", bucketName, sourceFile.getPath(), descPath, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String uploadLoaclFileToMinio(String bucketName, String sourcePath, String descPath) {
        try {
            /** @Desc 创建桶   */
            /** @Desc 判断是否成功  */
            if (!createBucket(bucketName)) {
                log.info("创建桶失败，无法上传！" + bucketName);
                return null;
            }
            uploadFile(new File(sourcePath), descPath);
            return descPath;
        } catch (Exception e) {
            log.info("文件上传失败，桶名称:{},源文件:{},目标路径为：{}，错误信息为：{}，{}", bucketName, sourcePath, descPath, e.getMessage(), e);
        }
        return null;
    }

    private void uploadFile(File sourceFile, String descPath) {
        InputStream in = null;
        try {
            in = new FileInputStream(sourceFile);
            uploadFile(in, descPath);
        } catch (Exception e) {
            log.info("文件上传失败，错误信息为{}，{}", e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }

    }

    private void uploadFile(InputStream in, String descPath) {
        try {
            //创建头部信息
            Map<String, String> headers = new HashMap<>(10);
            //添加自定义内容类型
            headers.put("Content-Type", "text/xml");
            /*常见的媒体格式类型如下：
            //application/octet-stream
            text/html ： HTML格式
            text/plain ：纯文本格式
            text/xml ： XML格式
            image/gif ：gif图片格式
            image/jpeg ：jpg图片格式
            image/png：png图片格式
            以application开头的媒体格式类型：

            application/xhtml+xml ：XHTML格式
            application/xml： XML数据格式
            application/atom+xml ：Atom XML聚合格式
            application/json： JSON数据格式
            application/pdf：pdf格式
            application/msword ： Word文档格式
            application/octet-stream ： 二进制流数据（如常见的文件下载）
            application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
            */
            //添加存储类
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");
            //添加自定义/用户元数据
            Map<String, String> userMetadata = new HashMap<>(10);
            userMetadata.put("My-Project", "Project One");
            //上传
            minioClient.putObject(                   //.object(fileName)   linux 文件路径
                    PutObjectArgs.builder().bucket(bucket).object(descPath).stream(
                            in, in.available(), -1)
                            .headers(headers)
                            .userMetadata(userMetadata)
                            .build());
            in.close();
        } catch (Exception e) {
            log.info("文件上传失败，错误信息为{}，{}", e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }

    }


    /**
     * 上传文件
     *
     * @return
     * @Param bucketName  桶名称
     * @Param stream   文件内容
     */
    @Override
    public String uploadStingTo(String bucketName, String filePath, String stream) {
        InputStream inputStream = null;
        try {

            /** @Desc 创建桶   */
            /** @Desc 判断是否成功  */
            if (!createBucket(bucketName)) {
                log.info("创建桶失败，无法上传！" + bucketName);
                return null;
            }

            inputStream = new ByteArrayInputStream(stream.getBytes("UTF-8"));
            uploadFile(inputStream, filePath);
            return "上传成功"+filePath;
        } catch (Exception e) {
            log.info("文件下载失败，桶名称:{},文件名:{},错误信息为：{}，{}", bucketName, filePath, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 创建存储桶
     *
     * @param bucketName
     * @return
     */
    private Boolean createBucket(String bucketName) {
        int i = 0;
        try {
            while (i < 5) {
                Boolean sbl = minioClient.bucketExists(bucketName);
                if (!sbl) {
                    // 创建一个名为bucketName 的存储桶
                    minioClient.makeBucket(bucketName);
                } else {
                    log.info("创建成功！" + bucketName);
                    return true;
                }
                i++;
            }
            log.info("创建桶失败，无法上传！" + bucketName);
            return false;
        } catch (Exception e) {
            log.info("创建桶{}失败，错误信息为{}，{}", bucketName, e.getMessage(), e);
            return false;
        }
    }


    /**
     * 上传文件是否存在
     *
     * @return
     * @Param bucketName  桶名称
     * @Param stream   文件内容
     */
    @Override
    public String uploadFileMinio_Source_Exist(String bucketName, String filePath) {

        try {
            /** @Desc 创建桶   */
            /** @Desc 判断是否成功  */
            if (!createBucket(bucketName)) {
                log.info("创建桶失败，无法上传！" + bucketName);
                return null;
            }

            ///获取下载文件的url，直接点击该url即可在浏览器中下载文件
            String url = "";
            try {
                url = minioClient.presignedGetObject(bucketName, filePath);
                System.out.println("url=" + url.toString());
            } catch (Exception e) {
                e.getMessage();
                return null;
            }

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }


    /**
     * 上传文件是否存在
     *
     * @return
     * @Param bucketName  桶名称
     * @Param stream   文件内容
     */
    @Override
    public Boolean isExist(String bucketName, String filePath) {
        try {
            /** @Desc 创建桶   */
            /** @Desc 判断是否成功  */
            if (!minioClient.bucketExists(bucketName)) {
                log.info("{}桶不存在！", bucketName);
                return false;
            }
            ///获取下载文件的url，直接点击该url即可在浏览器中下载文件
            String url = minioClient.presignedGetObject(bucketName, filePath);
            if (StringUtils.isNotEmpty(url)) {
                return true;
            }
        } catch (Exception e) {
            log.info("文件是否存在失败，桶名称:{},文件名:{},错误信息为：{}，{}", bucketName, filePath, e.getMessage(), e);
        }
        return false;
    }

    /**
     * 下载
     *
     * @return
     * @Param bucketName  桶名称
     * @Param fileName  文件名
     */
    @Override
    public String downloadMinio(String bucketName, String fileName) {
        //文件名称再次整理
        fileName = FileFunctionUtil.Get_FilePath_FileName(fileName);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            byte[] buf = new byte[1024];
            int bytesRead;
            StringBuilder sb = new StringBuilder();
            while ((bytesRead = inputStream.read(buf, 0, buf.length)) >= 0) {
                String strTmp1 = new String(buf, 0, bytesRead, StandardCharsets.UTF_8);
                sb.append(strTmp1);
            }
            return sb.toString();
        } catch (Exception e) {
            log.info("文件下载失败，桶名称:{},文件名:{},错误信息为：{}，{}", bucketName, fileName, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }

    /**
     * 下载
     *
     * @return
     * @Param bucketName  桶名称
     * @Param fileName  文件名
     */
    @Override
    public InputStream getInputStream(String bucketName, String fileName) {
        //文件名称再次整理
        fileName = FileFunctionUtil.Get_FilePath_FileName(fileName);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return inputStream;
        } catch (Exception e) {
            log.info("文件下载失败，桶名称:{},文件名:{},错误信息为：{}，{}", bucketName, fileName, e.getMessage(), e);
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }
}