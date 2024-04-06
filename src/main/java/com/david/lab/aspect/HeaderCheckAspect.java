package com.david.lab.aspect;

import com.david.lab.exception.AopIsAwesomeHeaderException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HeaderCheckAspect {

    @Before("execution(* com.david.lab..*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void checkHeader() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra != null ? sra.getRequest() : null;
        String header = request != null ? request.getHeader("AOP-IS-AWESOME") : null;
        if (header == null) {
            throw new AopIsAwesomeHeaderException("AOP-IS-AWESOME header is missing");
        }
    }
}