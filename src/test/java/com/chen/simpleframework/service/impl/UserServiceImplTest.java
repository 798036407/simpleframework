package com.chen.simpleframework.service.impl;

import com.chen.simpleframework.domain.dto.UserDTo;
import com.chen.simpleframework.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author: cz
 * Date： 2020/12/11 21:35
 */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void save() {
        UserDTo userDTo = new UserDTo();
        userDTo.setUsername("dss");
        userDTo.setPassword("aaa");
        userDTo.setEmail("abc@123.com");
        userDTo.setAge(22);
        userDTo.setPhone("18812314423");
        userDTo.setVersion(1L);

        int save = userService.save(userDTo);


    }

    /**
     * 乐观锁使用的规则
     * 1、如果更新数据不带有version字段，不使用乐观锁，并且version不会累加
     * 2、如果更新字段中带有version，丹玉数据库中不一致，更新失败
     * 3、如果带有version，并且与数据库一致，更新成功，并且version会累加
     */
    @Test
    void update() {
        Long id = 1220708537638920197L;
        UserDTo userDTo = new UserDTo();
        userDTo.setAge(114);
        userDTo.setPassword("passWord");
        int update = userService.update(id, userDTo);
    }

    @Test
    void delete() {
        Long id = 1220708537638920191L;
        int delete = userService.delete(id);
    }

    @Test
    void query() {
    }
}