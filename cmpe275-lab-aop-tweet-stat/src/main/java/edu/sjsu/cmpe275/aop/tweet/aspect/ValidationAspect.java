package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(2)
public class ValidationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

//	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
//	public void dummyBeforeAdvice(JoinPoint joinPoint) {
//		System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
//	}

	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public void validateMessageLengthBeforeTweet(JoinPoint joinPoint) {
		System.out.printf("Validate posted tweet before the execution of the metohd %s\n", joinPoint.getSignature().getName());
		String user = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		if(user == null || message == null || message.length() > 140 || user.equals("") || message.equals("")){
			throw new IllegalArgumentException("Invalid parameters used either message or user is null or message length is more than 140"); }
	}

	//Check if the params are valid and they are not null
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void validateFollowParams(JoinPoint joinPoint) {
		System.out.printf("Check if the params are valid before the execution of the metohd %s\n", joinPoint.getSignature().getName());
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		if(followee == null || follower == null || followee.equals(follower) || followee.equals("") || follower.equals(""))
			throw new IllegalArgumentException("Invalid parameters or user tried to follow himself");
	}

	//Check if the params are valid and they are not null
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void validateBlockParams(JoinPoint joinPoint) {
		System.out.printf("Check if the params are valid before the execution of the metohd %s\n", joinPoint.getSignature().getName());
		String user = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		if(user == null || followee == null || followee.equals(user) ||  user.equals("") || followee.equals(""))
			throw new IllegalArgumentException("Invalid parameters or user tried to  block himself");
	}

	//Check if the params are valid and they are not null
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.unblock(..))")
	public void validateUnBlockParams(JoinPoint joinPoint) {
		System.out.printf("Check if the params are valid before the execution of the metohd %s\n", joinPoint.getSignature().getName());
		String user = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];

		if(user == null || followee == null || followee.equals(user) ||  user.equals("") || followee.equals(""))
			throw new IllegalArgumentException("Invalid parameters or user tried to  block himself");
	}


}
