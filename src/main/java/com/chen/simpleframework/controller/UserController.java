package com.chen.simpleframework.controller;

import com.chen.simpleframework.domain.common.PageQuery;
import com.chen.simpleframework.domain.common.PageResult;
import com.chen.simpleframework.domain.common.ResponseResult;
import com.chen.simpleframework.domain.dto.UserDTo;
import com.chen.simpleframework.domain.dto.UserQueryDTO;
import com.chen.simpleframework.domain.vo.UserVO;
import com.chen.simpleframework.exception.ErrorCodeEnum;
import com.chen.simpleframework.service.ExcelExportService;
import com.chen.simpleframework.service.UserService;
import com.chen.simpleframework.util.InsertValidationGroup;
import com.chen.simpleframework.util.UpdateValidationGroup;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: cz
 * Date： 2020/12/10 22:17
 * 用户Controller
 */
@RestController
@RequestMapping("/api/users")
@Validated
@Slf4j
@Api(
        value = "用户管理Controller",
        protocols = "http, https",
        hidden = false
)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelExportService excelExportService;

    /**
     * POST /api/users UserDTO
     */
    @CacheEvict(cacheNames = "users-cache", allEntries = true)
    @PostMapping
    public ResponseResult save(
            @Validated(InsertValidationGroup.class)
            @RequestBody UserDTo userDTo) {
        int save = userService.save(userDTo);
        if (save == 1) {
            return ResponseResult.success("新增成功");
        } else {
            return ResponseResult.error(ErrorCodeEnum.INSERT_FAILURE);
        }
    }

    /**
     * 更新用户信息
     * PUT  /api/users/{id}  UserDTO userDTO
     *
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation(
            value = "更新用户信息",
            notes = "备注信息",
            //标记返回信息
            response = ResponseResult.class,
            httpMethod = "PUT"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "参数说明，用户主键",
                    required = true,
                    //参数获取路径
                    paramType = "path",
                    dataType = "Long",
                    example = "123"
            ),
            @ApiImplicitParam(
                    name = "userDTo",
                    value = "用户信息",
                    required = true,
                    //参数获取路径
                    paramType = "body",
                    dataType = "UserDTo",
                    dataTypeClass = UserDTo.class
            )
    })
    @ApiResponses({
            @ApiResponse(code = 0000, message = "更新成功"),
            @ApiResponse(code = 3002, message = "更新失败")
    })
    public ResponseResult update(
            @NotNull  @PathVariable("id") Long id,
            @Validated(UpdateValidationGroup.class) @RequestBody UserDTo userDTo) {
        int update = userService.update(id, userDTo);
        if (update == 1) {
            return ResponseResult.success("更新成功");
        } else {
            return ResponseResult.error(ErrorCodeEnum.UPDATE_FAILURE);
        }
    }

    /**
     * 删除用户信息
     * DELETE  /api/users/{id}
     *
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult delete(
            @NotNull(message = "用户id不能为空！") @PathVariable("id") Long id) {
        int delete = userService.delete(id);
        if (delete == 1) {
            return ResponseResult.success("删除成功");
        } else {
            return ResponseResult.error(ErrorCodeEnum.DELETE_FAILURE);
        }
    }

    /**
     * 查询用户信息
     * GET
     *
     * @return
     */
    @Cacheable(cacheNames = "users-cache")
    @GetMapping
    public ResponseResult<PageResult> query(
            @NotNull Integer pageNo,
            @NotNull Integer pageSize,
            @Validated UserQueryDTO queryDTO) {
        log.info("未使用缓存！");
        //1、构造查询条件
        PageQuery<UserQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(queryDTO);
        //2、查询
        PageResult<List<UserDTo>> pageResult = userService.query(pageQuery);
        //3、实体转换
        List<UserVO> userVOList = Optional.ofNullable(pageResult.getData())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(userDTo -> {
                    UserVO userVO = new UserVO();

                    BeanUtils.copyProperties(userDTo, userVO);
                    //对特殊字段处理
                    userVO.setPassword("******");
                    if (StringUtils.hasLength(userVO.getPhone())) {
                        userVO.setPhone(userVO.getPhone().replace("(\\d{3})\\{4}(\\{4})"
                                , "$1****$2"));
                    }
                    return userVO;
                })
                .collect(Collectors.toList());

        //4、封装查询结果
        PageResult<List<UserVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);

        result.setData(userVOList);

        return ResponseResult.success(result);
    }

    /**
     * excel导出
     * @param query
     * @param filename
     * @return
     */
    @GetMapping("/export")
    public ResponseResult<Boolean> export(
            @Validated UserQueryDTO query,
            @NotEmpty String filename) {

        //数据导出
//        excelExportService.export(query, filename);
        excelExportService.asyncExport(query, filename);
        return ResponseResult.success(Boolean.TRUE);

    }
}
