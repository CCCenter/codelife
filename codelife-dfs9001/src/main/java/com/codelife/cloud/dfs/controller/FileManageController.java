package com.codelife.cloud.dfs.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.codelife.cloud.dfs.util.FileDfsUtils;
import com.codelife.cloud.entities.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileManageController {

    @Autowired
    private FileDfsUtils fileDfsUtils;

    /**
     * 文件上传
     */

    @PostMapping(value = "/uploadFile",headers="content-type=multipart/form-data")
    public CommonResult uploadFile (@RequestParam("file") MultipartFile file){
        try{
            String path = fileDfsUtils.upload(file);
            if (!StringUtils.isEmpty(path)){
                System.out.println(path);
                return new CommonResult(200,"上传成功",path);

            } else {
                return new CommonResult(200,"上传失败了请重试！");
            }
        } catch (Exception e){
            e.printStackTrace() ;
            return new CommonResult(500,"服务齐冒烟了啊，稍后再试");
        }
    }

    @GetMapping(value = "/deleteFile")
    public void deleteFile (String fileUrl){
        fileDfsUtils.deleteFile(fileUrl);
    }


    @GetMapping("/download")
    public void downFile(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        byte[] bytes = fileDfsUtils.downFile(url);
        //设置相应类型application/octet-stream        （注：applicatoin/octet-stream 为通用，一些其它的类型苹果浏览器下载内容可能为空）
        response.reset();
        response.setContentType("applicatoin/octet-stream");
        //设置头信息                 Content-Disposition为属性名  附件形式打开下载文件   指定名称  为 设定的fileName
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("123.png", "UTF-8"));
        // 写入到流
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.close();
    }
}
