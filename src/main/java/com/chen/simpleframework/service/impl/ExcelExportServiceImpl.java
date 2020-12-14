package com.chen.simpleframework.service.impl;

import ch.qos.logback.classic.Logger;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.chen.simpleframework.domain.common.PageQuery;
import com.chen.simpleframework.domain.common.PageResult;
import com.chen.simpleframework.domain.dto.UserDTo;
import com.chen.simpleframework.domain.dto.UserExportDTO;
import com.chen.simpleframework.domain.dto.UserQueryDTO;
import com.chen.simpleframework.service.ExcelExportService;
import com.chen.simpleframework.service.FileService;
import com.chen.simpleframework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: cz
 * Date： 2020/12/13 17:01
 */
@Slf4j
@Service
public class ExcelExportServiceImpl implements ExcelExportService {

    @Resource(name = "LocalFileServiceImpl")
    private FileService fileService;

    @Autowired
    private UserService userService;

    private void export(OutputStream outputStream, UserQueryDTO query) {

        //1、需要创建一个EasyExcel导出对象
        ExcelWriter excelWriter = EasyExcelFactory.write(outputStream, UserExportDTO.class).build();

        //2、分批加载数据
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setQuery(query);
        pageQuery.setPageSize(2);

        int pageNo = 0;
        PageResult<List<UserDTo>> pageResult;
        do {
            //先累加再赋值
            pageQuery.setPageNo(++pageNo);

            log.info("开始导出第【 {} 】页数据！",pageNo);
            pageResult = userService.query(pageQuery);
            //数据转换： UserDTO 转换成 UserExportDTO
            List<UserExportDTO> userExportDTOList = Optional.ofNullable(pageResult.getData())
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(userDTo -> {
                        UserExportDTO userExportDTO = new UserExportDTO();
                        BeanUtils.copyProperties(userDTo, userExportDTO);
                        return userExportDTO;
                    })
                    .collect(Collectors.toList());
            //3、导出分批加载的数据
            //将数据写入到不同的sheet页
            WriteSheet writerSheet = EasyExcelFactory.writerSheet(pageNo, "第" + pageNo + "页").build();
            excelWriter.write(userExportDTOList, writerSheet);
            log.info("结束导出第【 {} 】页数据！",pageNo);
            //总页数大于当前页 说明还有数据继续执行
        } while (pageResult.getPageNum() > pageNo);

        //4、收尾,执行finish，才会关闭Excel文件流
        excelWriter.finish();

        log.info("完成导出！");

    }

    @Override
    public void export(UserQueryDTO query, String filename) {
        //输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //1、实现数据导出到Excel中
        export(outputStream, query);

        //输入流(输出流转成输入流)
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        //2、实现文件上传
        fileService.upload(inputStream, filename);
    }

    /**
     * @Async标注，使用线程池执行方法
     * @param query
     * @param filename
     */
    @Async("exportServiceExecutor")
    @Override
    public void asyncExport(UserQueryDTO query, String filename) {
        export(query, filename);
    }
}
