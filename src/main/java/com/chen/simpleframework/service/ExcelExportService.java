package com.chen.simpleframework.service;

import com.chen.simpleframework.domain.dto.UserQueryDTO;

/**
 * Excel导出服务接口
 * @author: cz
 * Date： 2020/12/13 16:59
 */
public interface ExcelExportService {

    /**
     * 同步导出服务
     * @param filename
     */
    void export(UserQueryDTO query, String filename);

    /**
     * 异步导出服务
     * @param query
     * @param filename
     */
    void asyncExport(UserQueryDTO query, String filename);
}
