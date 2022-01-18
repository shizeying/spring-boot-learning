package com.example.jwt.handler;

import com.example.jwt.constant.CommonEnum;
import com.example.jwt.constant.RestResp;
import io.jsonwebtoken.SignatureException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestResp<?> exceptionHandler(MethodArgumentNotValidException validException) {
        log.error(validException.getMessage());
        return RestResp.error(validException.getMessage());
    }
    @ExceptionHandler(value = {UsernameNotFoundException.class,
        BadCredentialsException.class,
        DisabledException.class,
        AuthenticationException.class,
        ServletException.class,
        SignatureException.class
        })
    public RestResp<?> exceptionLoginHandler(HttpServletRequest req,Throwable badTokenException) {
        log.error(badTokenException.getMessage());
        return RestResp.error(HttpStatus.UNAUTHORIZED.value(),badTokenException.getMessage());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public RestResp<?> exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.error("发生空指针异常！原因是:",e);
        return RestResp.error(CommonEnum.BODY_NOT_MATCH);
    }

}
