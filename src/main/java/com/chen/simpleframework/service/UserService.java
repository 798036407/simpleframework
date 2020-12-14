package com.chen.simpleframework.service;

import com.chen.simpleframework.domain.common.PageQuery;
import com.chen.simpleframework.domain.common.PageResult;
import com.chen.simpleframework.domain.dto.UserDTo;
import com.chen.simpleframework.domain.dto.UserQueryDTO;
import lombok.Data;

import java.util.List;

/**
 * @author: cz
 * Date： 2020/12/11 20:29
 * 用户服务接口
 */
public interface UserService {

    /**
     * 新增
     * @param userDTo
     * @return
     */
    int save(UserDTo userDTo);

    /**
     * 更新
     * @param id
     * @param userDTo
     * @return
     */
    int update(Long id, UserDTo userDTo);

    /**
     * 删除
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResult<List<UserDTo>> query(PageQuery<UserQueryDTO> pageQuery);

}
