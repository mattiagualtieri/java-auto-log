package com.guti.processor;

import com.guti.AutoLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
public class AutoLogAspect {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private final LoggersCache loggersCache = new LoggersCache();

  @Around("@annotation(annotation)")
  public Object log(ProceedingJoinPoint joinPoint, AutoLog annotation) throws Throwable {
    Class<?> targetClass = joinPoint.getTarget().getClass();
    Logger logger = loggersCache.getLoggerForClass(targetClass);
    String methodName = joinPoint.getSignature().getName();
    String args = resolveArgs(joinPoint, annotation);
    logger.info("Called {}({})", methodName, args);
    try {
      Object result = joinPoint.proceed();
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      if (!void.class.equals(signature.getReturnType())) {
        logger.info("Call to {} returned {}", methodName, result);
      }
      return result;
    } catch (Throwable t) {
      logger.error(t.getMessage(), t);
      throw t;
    }
  }

  private String resolveArgs(ProceedingJoinPoint joinPoint, AutoLog annotation) {
    List<String> result = new ArrayList<>();
    Object[] args = joinPoint.getArgs();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Class<?>[] parameterTypes = signature.getParameterTypes();
    for (int i = 0; i < args.length; i++) {
      result.add(resolveArg(i, parameterTypes[i], args[i], annotation));
    }
    return String.join(", ", result);
  }

  private String resolveArg(int ordinal, Class<?> parameterType, Object arg, AutoLog annotation) {
    Optional<AutoLog.Mapping> oMapping =
        Arrays.stream(annotation.mappings())
            .filter(mapping -> mapping.type().equals(parameterType))
            .filter(mapping -> mapping.order() == ordinal || mapping.order() == -1)
            .max(Comparator.comparingInt(AutoLog.Mapping::order));

    if (oMapping.isPresent()) {
      AutoLog.Mapping mapping = oMapping.get();
      try {
        Method method = parameterType.getDeclaredMethod(mapping.method());
        return method.invoke(arg).toString();
      } catch (Exception e) {
        log.warn(e.getMessage(), e);
        return arg.toString();
      }
    }

    return arg.toString();
  }
}
