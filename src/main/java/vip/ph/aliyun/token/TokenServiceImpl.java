package vip.ph.aliyun.token;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import vip.ph.aliyun.exception.MyException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService{

    @Resource
    RedisService redisService;

    @Override
    public String createToken() {

        String uuid = UUID.randomUUID().toString();
        redisService.setEx(uuid,uuid,10000L);

        return uuid;
    }

    @Override
    public Boolean checkToken(HttpServletRequest request) {

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
          token =  request.getParameter("token");
        }else if(StringUtils.isEmpty(token)){
            throw new MyException(500,"Token不存在");
        }

        if (!redisService.exists(token)){
            throw new MyException(500,"重复操作!");
        }

        boolean remove = redisService.remove(token);

        if (!remove){
            throw new MyException(500,"重复操作!");
        }


        return true;
    }
}
