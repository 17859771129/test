package com.ylz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.demo.entity.ManagerFun;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerFunMapper extends BaseMapper<ManagerFun> {

    List<ManagerFun> queryByRoleId(@Param("roleId") Integer roleId);
}