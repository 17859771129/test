package com.ylz.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("boolean")
public class Boolean1 {
    int id;
    boolean aBoolean;
}
