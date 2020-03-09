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

//	@Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.*tweet(..))")
//	public int dummyAdviceOne(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
//		Integer result = null;
//		try {
//			result = (Integer) joinPoint.proceed();
//			System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
//		} catch (Throwable e) {
//			e.printStackTrace();
//			System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
//			throw e;
//		}
//		return result.intValue();
//	}

	@Around("execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	public Object tweetAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Around %s\n" , joinPoint.getSignature().getName());

		Object i;
		try {
			i = joinPoint.proceed();

		} catch (IOException e1) {
			System.out.println("Trying to connect again: 1st attempt");
			try {
				i = joinPoint.proceed();
			} catch (IOException e2) {
				System.out.println("Trying to connect again: 2nd attempt");
				try {
					i = joinPoint.proceed();
				} catch (IOException e3) {
					System.out.println("Trying to connect again: 3nd attempt");
					try {
						i = joinPoint.proceed();
					} catch (IOException e4) {
						System.out.println("Tweet failed permanently");
						throw new IOException("Network Error has occured");
					}
				}
			}
		}
		return i;
	}

}
