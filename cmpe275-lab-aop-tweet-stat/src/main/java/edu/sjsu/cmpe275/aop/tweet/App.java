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
//            tweeter.follow("bob", "alex");
//            tweeter.follow("Adi", "alex");
//            tweeter.follow("bob", "Zhang");
//
//            tweeter.tweet("alex", "First Tweet");
//
//            tweeter.block("Carl", "bob");
////            tweeter.block("alex", "bob");
////            tweeter.block("Zhang", "bob");
//            tweeter.tweet("alex", "second tweet");
//            tweeter.follow("Joe", "alex");
////            tweeter.block("Carl", "Adi");
//            tweeter.tweet("alex", "Third tweet");
////            tweeter.block("alex", "Adi");
////            tweeter.unblock("alex", "Carl");
//            tweeter.tweet("alex", "Forth tweet");
//            tweeter.unblock("alex", "bob");
//            tweeter.unblock("alex", "Adi");
//            tweeter.unblock("Zhang", "bob");
//            tweeter.tweet("alex", "Fifth tweet");
////            tweeter.block("alex", "bob");
////            tweeter.block("alex", "Adi");
////            tweeter.tweet("bob", "second tweet");

            tweeter.follow("bob", "alex");
            tweeter.follow("carl", "alex");
            tweeter.tweet("alex", "First Tweet");
            tweeter.block("alex", "bob");
            tweeter.block("carl", "bob");
            tweeter.block("alex", "carl");
            tweeter.tweet("alex", "second tweet");
            tweeter.tweet("bob", "second tweet");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
        System.out.println("Most popular message: " + stats.getMostPopularMessage());
        System.out.println("Most blocked by missed tweets: " + stats.getMostBlockedFollowerByNumberOfMissedTweets());
        System.out.println("Most blocked follower: " + stats.getMostBlockedFollowerByNumberOfFollowees());
        ctx.close();
    }
}
