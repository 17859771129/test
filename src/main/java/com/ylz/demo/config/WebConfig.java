/*
package com.ylz.demo.config;

import com.ylz.demo.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//配置拦截器
@Configuration
public class WebConfig {

    @Autowired
    private LoginFilter loginFilter;
    //自定义拦截器
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(loginFilter);
        filterRegistrationBean.setName("loginFilter");
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
    //连接超时时间，读取超时时间等等的配置
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    //如果有两个，这个优先
    @Primary
    //@ConditionalOnMissingBean说明，当不存在ObjectMapper时便执行该方法的实例化的操作，也就是说会通过Jackson2ObjectMapperBuilder来创建一个ObjectMapper对象
    @ConditionalOnMissingBean(ObjectMapper.class)
    //使用Jackson实现Json与Bean之间转换时，最主要的类便是Jackson的ObjectMapper,以下为配置
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            //jackson ObjectMapper 序列化成json，属性值为null时转换为""空字符串
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }

}
*/
