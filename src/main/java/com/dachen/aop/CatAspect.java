package com.dachen.aop;

import java.lang.reflect.Method;

import com.dianping.cat.message.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CatAspect {

	@Around(value = "@annotation(com.dachen.aop.CatAnnotation)")
	public Object aroundMethod(ProceedingJoinPoint pjp) {
		MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
		Method method = joinPointObject.getMethod();

		Transaction t = Cat.newTransaction("Method", method.getName());

		try {
			Object obj = pjp.proceed();

			t.setStatus(Message.SUCCESS);
			t.complete();

			return obj;
		} catch (Throwable e) {
			t.setStatus(e);
			Cat.logError(e);
		} finally {
			t.complete();
		}

		return null;
	}

}
