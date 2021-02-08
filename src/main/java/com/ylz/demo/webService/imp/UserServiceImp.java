package com.ylz.demo.webService.imp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.ylz.demo.entity.User;
import com.ylz.demo.mapper.UserMapper;
import com.ylz.demo.webService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

/**
 * webService接口实现
 */
@WebService(serviceName = "UserService",//对外发布的服务名
        targetNamespace = "http://webService.demo.ylz.com/",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.ylz.demo.webService.UserService")
//服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@Component
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAllUsers() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        return userMapper.selectList(queryWrapper);
    }
    @Override
    public String demo(String kafka){
        return "ok"+kafka;
    }
}
