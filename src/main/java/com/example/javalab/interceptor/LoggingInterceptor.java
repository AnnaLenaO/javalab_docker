package com.example.javalab.interceptor;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Log
public class LoggingInterceptor {

    @AroundInvoke
    public Object logMethodEntry(InvocationContext invocationContext) throws Exception {
        Class<?> targetClass = getTargetClass(invocationContext.getTarget());
        Logger logger = LoggerFactory.getLogger(targetClass);

        logger.info("Entering method: {} of class: {}",
                invocationContext.getMethod().getName(), targetClass.getName());
        try {
            return invocationContext.proceed();
        } finally {
            logger.info("Exiting method: {} of class: {}",
                    invocationContext.getMethod().getName(), targetClass.getName());
        }
    }

    private Class<?> getTargetClass(Object proxy) {
        Class<?> clazz = proxy.getClass();
        if (clazz.getName().contains("$")) {
            return clazz.getSuperclass();
        }
        return clazz;
    }
}
