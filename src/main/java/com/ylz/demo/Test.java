package com.ylz.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ylz.demo.entity.User;
import com.ylz.demo.mapper.UserMapper;
import com.ylz.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        System.out.println("fddf");
        Map<String,String> map = new HashMap<>();
        map.put("a","we");
        System.out.println(map.containsKey("a"));
    }
}
