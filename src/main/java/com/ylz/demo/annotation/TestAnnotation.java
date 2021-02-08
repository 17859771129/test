package com.ylz.demo.annotation;
import java.lang.annotation.*;

/**
 * 自定义注解
 */
//指明了修饰的这个注解的使用范围
@Target(ElementType.METHOD)
//指明修饰的注解的生存周期
@Retention(RetentionPolicy.RUNTIME)
//指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值。
@Documented
public @interface TestAnnotation {
    String value() default "";
}
