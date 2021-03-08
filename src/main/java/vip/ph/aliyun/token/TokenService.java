package vip.ph.aliyun.token;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String createToken();


    Boolean checkToken(HttpServletRequest request);
}
