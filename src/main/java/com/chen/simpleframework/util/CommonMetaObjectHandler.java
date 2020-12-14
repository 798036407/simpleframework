package com.chen.simpleframework.util;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author: cz
 * Date： 2020/12/11 21:46
 * 公共元数据处理器
 */
@Component
@Slf4j
public class CommonMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入的时候填充哪些字段
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新建时，开始填充系统字段");

        //严格插入填充（metaObject对象，字段名，字段类型，字段值）
        this.strictInsertFill(metaObject, "created", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "modified", LocalDateTime.class, LocalDateTime.now());

        this.strictInsertFill(metaObject, "creator", String.class, "TODO从上下文获取当前用户");
        this.strictInsertFill(metaObject, "operator", String.class, "TODO从上下文获取当前用户");

        this.strictInsertFill(metaObject, "status", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Long.class, 1L);
    }

    /**
     * 更新的时候填充哪些字段
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新时，开始填充系统字段");

        this.strictUpdateFill(metaObject, "modified", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "operator", String.class, "TODO从上下文获取修改人");
    }
}
