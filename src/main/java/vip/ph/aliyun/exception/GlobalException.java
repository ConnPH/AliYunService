package vip.ph.aliyun.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.ph.aliyun.utils.R;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exception(Exception e){
        return R.Error().message(e.getMessage());
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R Myexception(MyException e){
        return R.Error().message(e.getMessage()).code(e.getCode());
    }
}
