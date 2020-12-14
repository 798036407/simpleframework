package com.chen.simpleframework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chen.simpleframework.domain.common.PageQuery;
import com.chen.simpleframework.domain.common.PageResult;
import com.chen.simpleframework.domain.dto.UserDTo;
import com.chen.simpleframework.domain.dto.UserQueryDTO;
import com.chen.simpleframework.domain.entity.UserDo;
import com.chen.simpleframework.mapper.UserMapper;
import com.chen.simpleframework.service.UserService;
import com.chen.simpleframework.util.ValidatorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import java.sql.Wrapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: cz
 * Date： 2020/12/11 20:47
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int save(UserDTo userDTo) {
        UserDo userDo = new UserDo();

        //TODO 浅拷贝 属性名需要相同
        BeanUtils.copyProperties(userDTo ,userDo);
        //插入
        return userMapper.insert(userDo);
    }

    @Override
    public int update(Long id, UserDTo userDTo) {
        UserDo userDo = new UserDo();

        BeanUtils.copyProperties(userDTo, userDo);

        userDo.setId(id);
        //根据主键更新数据
        return userMapper.updateById(userDo);
    }

    @Override
    public int delete(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public PageResult<List<UserDTo>> query(PageQuery<UserQueryDTO> pageQuery) {

        //手动校验功能
        ValidatorUtils.validate(pageQuery);

        //1、参数构造
        Page page = new Page(pageQuery.getPageNo(),pageQuery.getPageSize());

        UserDo query = new UserDo();
        BeanUtils.copyProperties(pageQuery.getQuery(), query);
        //TODO 如果属性不一致，需要做特殊处理
        QueryWrapper queryWrapper = new QueryWrapper(query);

        //2、查询
        IPage<UserDo> userDoIPage = userMapper.selectPage(page, queryWrapper);

        //3、结果解析
        PageResult pageResult = new PageResult();
        pageResult.setPageNo((int) userDoIPage.getCurrent());
        pageResult.setPageSize((int) userDoIPage.getSize());
        pageResult.setTotal(userDoIPage.getTotal());
        pageResult.setPageNum(userDoIPage.getPages());

        //对数据进行转换（userDO转换成userDTO）
        List<UserDTo> userDToList = Optional.ofNullable(userDoIPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDo -> {
                    UserDTo userDTo = new UserDTo();
                    BeanUtils.copyProperties(userDo, userDTo);
                    return userDTo;
                })
                .collect(Collectors.toList());

        pageResult.setData(userDToList);

        return pageResult;
    }
}
