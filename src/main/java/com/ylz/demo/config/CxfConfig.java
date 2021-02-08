package com.ylz.demo.config;

import com.ylz.demo.webService.UserService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * webservice服务配置
 * CXF 是一个开放源代码框架，提供了用于方便地构建和开发 Web 服务的可靠基础架构。
 * 它允许创建高性能和可扩展的服务，
 */
/*配置类的方法名对应配置文件的id名
    配置类的返回值对应配置文件的class名*/
@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    private UserService userService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
        endpoint.publish("/UserService");
        return endpoint;
    }

    /**
     * 发布的路径前缀
     * 默认为services，可设置
     * @return
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }
}
