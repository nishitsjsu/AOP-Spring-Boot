package edu.sjsu.cmpe275.aop.tweet;

import java.io.IOException;
import java.util.Random;

public class TweetServiceImpl implements TweetService {

    /***
     * Following is a dummy implementation.
     * You can tweak the implementation to suit your need, but this file is NOT part of the submission.
	 * @return
	 */

//	@Override
//    public void tweet(String user, String message) throws IllegalArgumentException, IOException {
//    	System.out.printf("User %s tweeted message: %s\n", user, message);
//    }
	@Override
	public int tweet(String user, String message) throws IllegalArgumentException, IOException {
//    	System.out.printf("User %s tweeted message: %s\n", user, message);
//    	return 1;

		Random r = new Random();
		int i = r.nextInt(1000) + 1;
		System.out.printf("User %s tweeted message: %s\n", user, message);
		System.out.printf("result obtained is %s\n",i);
//		if(i<1000)
//			throw new IOException("testing IO");
		return i;

	}

	@Override
    public void follow(String follower, String followee) throws IOException {
       	System.out.printf("User %s followed user %s \n", follower, followee);
    }

	@Override
	public void block(String user, String follower) throws IOException {
       	System.out.printf("User %s blocked user %s \n", user, follower);		
	}

	@Override
	public void unblock(String user, String follower)
			throws IllegalArgumentException, UnsupportedOperationException, IOException {
    	System.out.printf("User %s unblocked user %s \n", user, follower);		
	}


}
