package com.pingpong.jlab.pingpong.global.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class TimeTraceAspect {

    @Around("execution(* com.pingpong.jlab.pingpong.domain..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        try{
            return joinPoint.proceed();
        }finally{
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("Method ::: " + joinPoint.toString() + " Elasped Time ::: " + timeMs + "ms");
        }
    }
}
