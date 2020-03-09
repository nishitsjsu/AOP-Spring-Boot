package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;
import java.io.IOException;
import java.util.*;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

	@Around("execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public Object tweetAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Around %s\n" , joinPoint.getSignature().getName());

		Object o;
		try {
			o = joinPoint.proceed();

		} catch (IOException e1) {
			System.out.println("Trying to connect again: 1st attempt");
			try {
				o = joinPoint.proceed();
			} catch (IOException e2) {
				System.out.println("Trying to connect again: 2nd attempt");
				try {
					o = joinPoint.proceed();
				} catch (IOException e3) {
					System.out.println("Trying to connect again: 3nd attempt");
					try {
						o = joinPoint.proceed();
					} catch (IOException e4) {
						System.out.println("Couldn't make it in 3 attempts");
						throw new IOException("Network Error has occured");
					}
				}
			}
		}
		return o;
	}

}
