package com.david.lab.aspect;

import com.david.lab.log.ActivityLog;
import com.david.lab.log.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class ExecutionTimeAspect {

    private final ActivityLogRepository activityLogRepository;

    @Pointcut("@annotation(com.david.lab.aspect.ExecutionTime)")
    public void executionTimePointcut() {
    }

    @Around("executionTimePointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        ActivityLog activityLog = new ActivityLog();
        activityLog.setDate(LocalDateTime.now());
        activityLog.setOperation(joinPoint.getSignature().getName());
        activityLog.setDuration(executionTime + " ms");

        activityLogRepository.save(activityLog);
        return proceed;
    }
}