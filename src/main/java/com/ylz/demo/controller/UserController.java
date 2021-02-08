package com.ylz.demo.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mysql.cj.xdevapi.JsonArray;
import com.ylz.demo.entity.ManagerFun;
import com.ylz.demo.entity.User;
import com.ylz.demo.kafka.consumer.TestConsumer;
import com.ylz.demo.mapper.ManagerFunMapper;
import com.ylz.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value="/demo")
public class UserController {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private ManagerFunMapper managerFunMapper;

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @RequestMapping(value = "hello")
    public String demo() {
        return "hello";
    }
    @RequestMapping(value = "/findById/{id}")
    public String findById(@PathVariable int id) {
        List<User> user = null;
        user = (List<User>) userService.findById(id);
        return JSONArray.fromObject(user).toString();
    }

    /**
     * produces第一种使用，返回json数据，下边的代码可以省略produces属性，因为我们已经使用了注解@responseBody就是返回值是json数据：
     * produces第二种使用，返回json数据的字符编码为utf-8.
     * @return
     */
    @RequestMapping(value="/findAllUsers", method = RequestMethod.GET,produces = "application/json" + ";charset=UTF-8")
    public List<User>  findAllUsers(int page,int size){
        //IPage<User> user= null;
        //List<User> user = null;
        IPage<User> user = userService.findAll(page,size);//page等于0跟1都是显示第一页
        return user.getRecords();
        //return JSONArray.fromObject(user).toString();//传JSON格式
    }
    @RequestMapping(value = "/666")
    public String find(String kafka) {
        if(kafka==null)
            return "666";
        return "ok"+kafka;
    }
    @RequestMapping(value = "/produce")
    public void sendMessage(String message){
        log.info("生产消息：[]",message);
        kafkaTemplate.send("demo",message);
    }
   /* @RequestMapping(value = "/consume")
    @KafkaListener(topics = "demo")
    public void consumer(ConsumerRecord consumerRecord){
        Optional kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            Object message =kafkaMessage.get();
            log.info(message.toString());
        }
    }*/
   @RequestMapping(value = "/fun")
   public List<ManagerFun> fun(Integer id) {
        List<ManagerFun> a = managerFunMapper.queryByRoleId(id);
        System.out.println(a);
       return a;
   }
}
