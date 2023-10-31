package com.task.person.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class Log {

	@Around("@annotation(com.task.person.aspect.LogMethodParam)")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		ObjectMapper om = new ObjectMapper();
		log.info("Method Arguments are {}", om.writeValueAsString(point.getArgs()));
		Object response = point.proceed();
		log.info("Response is {}", om.writeValueAsString(response));
		return response;
	}

}
