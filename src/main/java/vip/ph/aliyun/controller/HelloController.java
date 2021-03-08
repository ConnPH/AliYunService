package vip.ph.aliyun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ph.aliyun.annotation.Idempotent;
import vip.ph.aliyun.token.TokenService;

import javax.annotation.Resource;

@RestController
@RequestMapping("hello")
public class HelloController {


    @Resource
    private TokenService tokenService;

    @GetMapping("/getToken")
    public String getTOken(){
        String token = tokenService.createToken();
        return token;
    }


    @Idempotent
    @PostMapping("hello01")
    public String hello01(){
        return "Hello1";
    }

    @PostMapping("hello02")
    public String hello02(){
        return "Hello2";
    }
}
