package org.zdd.bookstore.common.utils.aop;

import org.apache.ibatis.ognl.EnumerationIterator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.zdd.bookstore.common.utils.IpUntils;
import org.zdd.bookstore.common.utils.OperLogAnnotation;
import org.zdd.bookstore.model.entity.OperationLog;
import org.zdd.bookstore.model.entity.User;
import org.zdd.bookstore.model.service.OperationLogService;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private OperationLogService logService;
    @Pointcut("@annotation(org.zdd.bookstore.common.utils.OperLogAnnotation)")
    public void operLogPointCut(){}
    @AfterReturning(value = "operLogPointCut()",returning = "keys")
    public void saveOperLog(JoinPoint joinPoint,Object keys){
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user== null) return;
        OperationLog operationLog = new OperationLog();
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            OperLogAnnotation operAnnotation = method.getAnnotation(OperLogAnnotation.class);
            if(operAnnotation!=null){
                operationLog.setOperTime(new Date());
                operationLog.setIp(IpUntils.getClientIpAddr(request));
                operationLog.setOperType(operAnnotation.operType()+request.getRequestURI());
                operationLog.setUserName(user.getUsername());
                operationLog.setUserType(user.getIdentity());
                logService.saveLog(operationLog);
            }
        }catch (Exception e){
            System.out.println("can not save operation log!");
        }

    }
}
