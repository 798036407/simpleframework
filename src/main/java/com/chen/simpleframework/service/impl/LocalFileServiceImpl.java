package com.chen.simpleframework.service.impl;

import com.chen.simpleframework.exception.BusinessException;
import com.chen.simpleframework.exception.ErrorCodeEnum;
import com.chen.simpleframework.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author: cz
 * Date： 2020/12/13 15:35
 * 本地文件上传服务实现类
 */
@Slf4j
@Service("LocalFileServiceImpl")
public class LocalFileServiceImpl implements FileService {

    /**
     * 存储空间
     */
    private static final String BUCKET = "uploads";

    @Override
    public void upload(InputStream inputStream, String filename) {
        //拼接文件的存储路径
        String storagePath = BUCKET + "/" + filename;
        try (
                //JDK8 TWR不能关闭外部资源的。 try()中定义可以自动关闭
                InputStream innerInputStream = inputStream;

                FileOutputStream fileOutputStream = new FileOutputStream(new File(storagePath));
        ) {
            //拷贝缓冲区
            byte[] buffer = new byte[1024];
            //读取文件流长度
            int len;
            //循环读取inputStream流中的数据写入到outputStream
            while ((len = innerInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            //冲刷流
            fileOutputStream.flush();
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILURE, e);
        }

    }

    @Override
    public void upload(File file) {
        try {
            upload(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILURE, e);
        }

    }
}
