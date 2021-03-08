package vip.ph.aliyun.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.ph.aliyun.token.TokenService;
import vip.ph.aliyun.utils.R;

import javax.annotation.Resource;

@RestController
public class IdempotentController {


    @Resource
    TokenService tokenService;


    @ApiOperation("创建Token")
    @GetMapping("getToken")
    public R getToken(){
        String token = tokenService.createToken();

        return R.OK().data("token",token);
    }

}
