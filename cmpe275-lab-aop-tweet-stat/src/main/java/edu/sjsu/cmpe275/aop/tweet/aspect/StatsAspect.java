package edu.sjsu.cmpe275.aop.tweet.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.AfterReturning;
import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Autowired TweetStatsServiceImpl stats;
	
//	@After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
//	public void dummyAfterAdvice(JoinPoint joinPoint) {
//		System.out.printf("After the executuion of the metohd follow %s\n", joinPoint.getSignature().getName());
//		//stats.resetStats();
//	}
	
//	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
//	public void dummyBeforeAdvice(JoinPoint joinPoint) {
//		System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
//	}


	// --------------------------------      Tweet Module --------------------------------------------------

	@Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
	public void validateMessageLengthBeforeTweet(JoinPoint joinPoint) {



		System.out.printf("Before the execution of the metohd %s\n", joinPoint.getSignature().getName());

		String user = (String) joinPoint.getArgs()[0];
		String message = (String) joinPoint.getArgs()[1];
		if(user == null || message == null || message.length() > 140 || user.equals("") || message.equals("")){
			throw new IllegalArgumentException("Invalid parameters used either message or user is null or message length is more than 140"); }

	}

	@AfterReturning(
			pointcut = "execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))",
			returning = "result")
	public void tweetAfterReturning(JoinPoint joinPoint, Object result) {
		stats.logUserTweet(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString(), (Integer) result);
		System.out.println("Tweet By " + joinPoint.getArgs()[0].toString() + " : " + joinPoint.getArgs()[1].toString() + " is logged");
	}


	// -------------------------------------------    Follow Module    ------------------------------------------------
	//Check if the params are valid and they are not null
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void validateFollowParams(JoinPoint joinPoint) {

		System.out.printf("Before the execution of the metohd %s\n", joinPoint.getSignature().getName());

		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];

		if(followee == null || follower == null || followee.equals(follower) || followee.equals("") || follower.equals(""))
			throw new IllegalArgumentException("Invalid parameters or user tried to follow himself");
	}


	/**
	 * Method After uses After aspect to check if follow operation has finished and update the tweet stats accordingly
	 *
	 * @param joinPoint
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void AfterFollow(JoinPoint joinPoint) {
		stats.logfollowHistory(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString());
		System.out.println(joinPoint.getArgs()[0].toString() + " following " + joinPoint.getArgs()[1].toString() + " is logged ------------");
	}

	// -------------------------------------------    Block Module    ------------------------------------------------
	//Check if the params are valid and they are not null
	@Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void validateBlockParams(JoinPoint joinPoint) {

		//System.out.printf("Before the execution of the metohd %s\n", joinPoint.getSignature().getName());

		String user = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		//System.out.println(joinPoint.getArgs()[0].toString().equals(joinPoint.getArgs()[1].toString()));

		if(user == null || followee == null || followee.equals(user) ||  user.equals("") || followee.equals(""))
			throw new IllegalArgumentException("Invalid parameters or user tried to  block himself");
	}


	/**
	 * Method After uses After aspect to check if follow operation has finished and update the tweet stats accordingly
	 *
	 * @param joinPoint
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void AfterBlock(JoinPoint joinPoint) {
		stats.logBlockHistory(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString());
		System.out.println(joinPoint.getArgs()[0].toString() + " blocking " + joinPoint.getArgs()[1].toString() + " is logged");
	}


}
