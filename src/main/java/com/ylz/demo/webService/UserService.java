package com.ylz.demo.webService;

import com.ylz.demo.entity.User;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * webService接口
 */
@WebService(name = "UserService", // 暴露服务名称
        targetNamespace = "http://webService.demo.ylz.com/")// 命名空间,一般是接口的包名倒序
public interface UserService {
    @WebMethod
    public List<User> findAllUsers();
    @WebMethod
    public String demo(String kafka);
}
