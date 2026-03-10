package dev.danilo.fintrack.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RepositoryLoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryLoggingAspect.class);

  @Pointcut("within(dev.danilo.fintrack.repository..*)")
  public void repositoryLayer() {}

  @Around("repositoryLayer()")
  public Object logRepository(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = proceedingJoinPoint.proceed();
    long duration = System.currentTimeMillis() - start;
    logger.info(
        "[Repository] {} executed in {} ms",
        proceedingJoinPoint.getSignature().toShortString(),
        duration);
    return result;
  }

  @AfterThrowing(pointcut = "repositoryLayer()", throwing = "exception")
  public void logRepositoryAfterThrowingException(JoinPoint joinPoint, Throwable exception) {
    String className = joinPoint.getSignature().getDeclaringTypeName();
    String methodName = joinPoint.getSignature().getName();

    logger.error(
        "[Repository] Exception in {}.{}() - {}: {}",
        className,
        methodName,
        exception.getClass().getSimpleName(),
        exception.getMessage(),
        exception);
  }
}
