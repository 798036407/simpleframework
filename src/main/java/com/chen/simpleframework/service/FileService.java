package com.chen.simpleframework.service;

import java.io.File;
import java.io.InputStream;

/**
 * @author: cz
 * Date： 2020/12/13 15:32
 * 文件上传服务接口
 */
public interface FileService {

    /**
     * 文件上传
     * @param inputStream
     * @param filename
     */
    void upload(InputStream inputStream, String filename);

    /**
     * 文件上传
     * @param file
     */
    void upload(File file);
}
