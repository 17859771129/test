package com.ylz.demo.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.ylz.demo.entity.Boolean1;
import com.ylz.demo.mapper.Boolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/1")
public class BooleanController {
    @Autowired
    private Boolean aBoolean;
    @RequestMapping("/1")
    public String function1(Boolean1 a){
        aBoolean.insert(a);
        return "ok";
    }
    @RequestMapping("/2")
    public Boolean1 function12(int a){
        return (Boolean1) aBoolean.selectById(a);
    }

}
