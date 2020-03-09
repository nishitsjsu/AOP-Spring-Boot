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

	// --------------------------------      Tweet Module --------------------------------------------------

	@AfterReturning(
			pointcut = "execution(* edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))",
			returning = "result")
	public void tweetAfterReturning(JoinPoint joinPoint, Object result) {
		stats.logUserTweet(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString(), (Integer) result);
		System.out.println("Tweet By " + joinPoint.getArgs()[0].toString() + " : " + joinPoint.getArgs()[1].toString() + " is logged");
	}


	// -------------------------------------------    Follow Module    ------------------------------------------------

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


	// -------------------------------------------    Unblock Module    ------------------------------------------------


	/**
	 * Method After uses After aspect to check if follow operation has finished and update the tweet stats accordingly
	 *
	 * @param joinPoint
	 */
	@AfterReturning("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.unblock(..))")
	public void AfterUnBlock(JoinPoint joinPoint) {
		stats.logUnBlockHistory(joinPoint.getArgs()[0].toString(), joinPoint.getArgs()[1].toString());
		System.out.println(joinPoint.getArgs()[0].toString() + " unblocking " + joinPoint.getArgs()[1].toString() + " is logged");
	}

}
