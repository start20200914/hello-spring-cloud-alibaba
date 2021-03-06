package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.controller;

import cn.hutool.core.util.IdUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pinganSecAcc")
@Slf4j
public class ImageController {

    @PostMapping("/idImageCheck")
    public String upload(@RequestParam("frontFile") MultipartFile file,
                         @RequestParam("backFile") MultipartFile file1,
                         @RequestParam("imageOrderNo")String imageOrderNo,
                         @RequestParam("fileType")String fileType,
                         @RequestParam("idNo")String idNo,
                         @RequestParam("idType")String idType,
                         @RequestParam("trueName")String trueName,
                         HttpServletRequest req)
            throws IllegalStateException, IOException {
        log.info("imageOrderNo:{},fileType:{},idNo:{},idType:{},trueName:{}",imageOrderNo,fileType,idNo,idType,trueName);
        // 判断文件是否为空，空则返回失败页面
        if (file.isEmpty()) {
            return "failed";
        }
        // 获取文件存储路径（绝对路径）
        String path = req.getServletContext().getRealPath("/WEB-INF/file");
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        String fileName1 = file1.getOriginalFilename();
        // 创建文件实例
        File filePath = new File(path, fileName);
        File filePath1 = new File(path, fileName1);
        // 如果文件目录不存在，创建目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录" + filePath);
        }
        // 写入文件
        file.transferTo(filePath);
        file1.transferTo(filePath1);
        return "success";
    }

    @GetMapping("/get")
    public String get(@RequestParam("other") String other) {
        log.info("other:{}", other);
        return "ok";
    }

    @PostMapping("/post")
    public String post(@RequestParam("other") String other) {
        log.info("other:{}", other);
        return "ok";
    }

    /**
     * base64加密传输 接收方new BASE64Decoder().decodeBuffer(data);解密
     * @param file
     * @param cookie
     * @param url
     * @return
     * @throws IOException
     */
    @PostMapping("/postBase64")
    public String postFile(@RequestParam("file") MultipartFile file,@RequestParam("cookie") String cookie,@RequestParam("url") String url) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(file.getBytes());
        Map<String,String> param = new HashMap<>();
        param.put("data",encode);
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Cookie","JSESSIONID="+cookie);
        return HttpClientUtil.uploadFile(url,null,param,headerParams);
    }

    @PostMapping("/decodeFromString")
    public String postFile(@RequestParam("str") String file)  {
        log.info("请求进入");
        byte[] bytes = Base64Utils.decodeFromString(file);
        String fileName = IdUtil.simpleUUID()+".pdf";
        getFileByBytes(bytes,"C:\\Users\\zy\\Desktop\\身份证",fileName);
        return "ok";
    }

    //将Byte数组转换成文件
    public static void getFileByBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
