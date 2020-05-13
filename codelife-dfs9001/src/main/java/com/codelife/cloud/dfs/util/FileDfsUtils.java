package com.codelife.cloud.dfs.util;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;


@Component
@PropertySource("classpath:application.yml")
public class FileDfsUtils {

//    @Value("${project.fastDfsUrl}")
    private String fastDfsUrl = "http://192.168.142.132/";

    @Autowired
    private FastFileStorageClient storageClient ;
    /**
     * 上传图片 会自动裁剪（这里文件不是图片会报异常）
     */
    public String uploadImage(MultipartFile multipartFile) throws Exception{
        String originalFilename = multipartFile.getOriginalFilename().
                substring(multipartFile.getOriginalFilename().
                        lastIndexOf(".") + 1);
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(
                multipartFile.getInputStream(),
                multipartFile.getSize(),originalFilename , null);
        return fastDfsUrl + storePath.getFullPath() ;
    }

    /**
     * 上传文件（通用）
     */
    public String upload(MultipartFile multipartFile) throws Exception{
        String originalFilename = multipartFile.getOriginalFilename().
                substring(multipartFile.getOriginalFilename().
                        lastIndexOf(".") + 1);
        StorePath storePath = this.storageClient.uploadFile( multipartFile.getInputStream(),
                multipartFile.getSize(),originalFilename , null);
//                uploadImageAndCrtThumbImage(
//                multipartFile.getInputStream(),
//                multipartFile.getSize(),originalFilename , null);
        return fastDfsUrl + storePath.getFullPath() ;
    }
    /**
     * @Description: 根据文件路径下载文件
     * @param filePath 文件路径
     * @return 文件字节数据
     * @throws Exception byte[]
     */
    public byte[] downFile(String filePath) throws Exception {
        StorePath storePath = StorePath.parseFromUrl(filePath);

        return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadCallback<byte[]>() {
            @Override
            public byte[] recv(InputStream ins) throws IOException {
                return IOUtils.toByteArray(ins);
            }
        });
    }

    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            //log.info("fileUrl == >>文件路径为空...");
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
        //    log.info(e.getMessage());
        }
    }
}

