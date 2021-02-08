package com.ylz.demo.interceptor;

import com.ylz.demo.annotation.TestAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * 切面，拦截器实现注解
 */
@Aspect
@Component
public class testInterceptor {
    //@Pointcut指明要对哪些类中的哪些方法进行增强，进行切割，指的是被增强的方法。即要切哪些东西。
    @Pointcut("@annotation(com.ylz.demo.annotation.TestAnnotation)")
    public void pointCut() {
    }
    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     * 执行顺序：
     * 1、环绕通知(point.proceed()之前代码)
     * 2、前置通知@Before("pointcut()")
     * 3、环绕通知(point.proceed()之后代码)
     * 4、后置通知@After("pointcut()")
     * 5、运行通知(或异常通知)@AfterThrowing("pointcut()")
     */
    @Around("pointCut()")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        TestAnnotation test = getDeclaredAnnotation(joinPoint);
        System.out.println("==@Around== lingyejun blog logger --》 method name " + methodName + " args " + params[0]);
        // 执行源方法
        joinPoint.proceed();
        // 模拟进行验证
        if (params != null && params.length > 0 && params[0].equals("Blog Home")) {
            System.out.println("==@Around== lingyejun blog logger --》 " + test.value() + " auth success");
        } else {
            System.out.println("==@Around== lingyejun blog logger --》 " + test.value() + " auth failed");
        }
    }
    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public TestAnnotation getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        TestAnnotation annotation = objMethod.getDeclaredAnnotation(TestAnnotation.class);
        // 返回
        return annotation;
    }
}
