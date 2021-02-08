package com.ylz.demo.service.imp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ylz.demo.entity.User;
import com.ylz.demo.mapper.UserMapper;
import com.ylz.demo.service.UserService;
import com.ylz.demo.tool.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;
import java.util.stream.Collectors;

//@Slf4j这个注解可以不用写private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);但是好像行不通
@Service("UserService")
public class UserServiceImp implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);
    /*private Map<String,List<Object>> a;
    private Map<String,Map<String,String>> b;
    a = list.stream().collect(Collect(Collectors.groupingBy(List::getOne)));*/


    /*
    redis必须下载安装才能使用，目前本地还没测试
    @Autowired
    private RedisUtil redisUtil;
    */
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findById(int id){
        log.info("找 id={}",id);//用占位符{}可以填充变量的值
        List list=userMapper.findById(id);
        log.info("找到 list={}",list);
        return list;
    }
  //IPage分页
    @Override
    public IPage<User> findAll(int page,int size) {
        log.warn("找所有");
        Page page1 = new Page<>(page,size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> user = userMapper.selectPage(page1,queryWrapper);
        log.info("{}",user.getTotal());
        return user;
    }
    /*
    //这个注解是该类程序开始之前的初始化，要求无输入无输出
    @PostConstruct
    public void init() {
        hfDocs = hfDocMapper.selectList(new QueryWrapper<>());
        //这个是将一个List<Object>划分为指定分组的Map
        hfMap = hfDocs.stream().collect(Collectors.groupingBy(HfDoc::getDocCategory));
    }*/
}
