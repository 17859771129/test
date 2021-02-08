package com.ylz.demo.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ylz.demo.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    public List<User> findById(int id);
    public IPage<User> findAll(int page,int size);
}
