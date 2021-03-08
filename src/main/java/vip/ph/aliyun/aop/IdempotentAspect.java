package vip.ph.aliyun.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vip.ph.aliyun.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class IdempotentAspect {


    @Resource
    TokenService tokenService;

    @Pointcut("@annotation(vip.ph.aliyun.annotation.Idempotent)")
    public void pc1(){}



    @Before("pc1()")
    public void before(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        tokenService.checkToken(request);
    }
}
