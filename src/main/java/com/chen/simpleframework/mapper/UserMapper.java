package com.chen.simpleframework.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chen.simpleframework.domain.entity.UserDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: cz
 * Date： 2020/12/11 15:09
 * UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDo> {

    //如果需要自定义一些方法
}
