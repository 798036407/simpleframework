package com.chen.simpleframework.controller;

import com.chen.simpleframework.domain.common.ResponseResult;
import com.chen.simpleframework.exception.BusinessException;
import com.chen.simpleframework.exception.ErrorCodeEnum;
import com.chen.simpleframework.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author: cz
 * Date： 2020/12/13 15:26
 * 文件服务Controller
 */
@RestController
@RequestMapping("/api/files")
@Slf4j
public class FileController {

    @Resource(name = "LocalFileServiceImpl")
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult<String> upload(@NotNull MultipartFile file) {
        //文件上传。。
        try {
            fileService.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ErrorCodeEnum.FILE_UPLOAD_FAILURE, e);
        }

        return ResponseResult.success(file.getOriginalFilename());
    }
}
