package edu.sjsu.cmpe275.aop.tweet;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

        try {
            tweeter.follow("bob", "alex");
            tweeter.follow("Adi", "alex");
            tweeter.follow("Joe", "alex");
            tweeter.tweet("alex", "First Tweet");

            tweeter.block("Carl", "bob");
            tweeter.block("alex", "Carl");
            tweeter.block("alex", "bob");
            tweeter.tweet("alex", "second tweet");
//            tweeter.block("alex", "Joe");
            tweeter.tweet("alex", "Third tweet");
            tweeter.tweet("alex", "Forth tweet");
//            tweeter.block("alex", "bob");
//            tweeter.block("alex", "Adi");
//            tweeter.tweet("bob", "second tweet");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most popular message: " + stats.getMostPopularMessage());
        System.out.println("Most blocked by missed tweets: " + stats.getMostBlockedFollowerByNumberOfMissedTweets());
        ctx.close();
    }
}
