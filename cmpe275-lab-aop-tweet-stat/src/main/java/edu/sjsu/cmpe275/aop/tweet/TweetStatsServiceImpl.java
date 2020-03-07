package edu.sjsu.cmpe275.aop.tweet;
import java.util.*;

public class TweetStatsServiceImpl implements TweetStatsService {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */

	private static int lengthOfLongestTweet = 0;

	//keep track of user message map
	private static TreeMap<String, HashSet<Integer>> userMessageMap = new TreeMap<String, HashSet<Integer>>();

	//keep track of messages added
	private static HashMap<Integer, String> messageMap = new HashMap<Integer, String>();

	//To keep track of message shared history
	private static TreeMap<Integer, HashSet<String>> messageSharedHistory = new TreeMap<Integer, HashSet<String>>();

	//To keep track of user follow history
	private static TreeMap<String, HashSet<String>> followHistoryMap = new TreeMap<String, HashSet<String>>();

	//To keep track of user block history if A blocks B this map has list who all has blocked B
	private static TreeMap<String, HashSet<String>> BlockHistoryMap = new TreeMap<String, HashSet<String>>();

	//To keep track of user block history if A blocks B this map has list who all  A has blocked
	private static TreeMap<String, HashSet<String>> BlockHistoryMapByUser = new TreeMap<String, HashSet<String>>();



	@Override
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMostFollowedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostPopularMessage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostBlockedFollowerByNumberOfMissedTweets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMostBlockedFollowerByNumberOfFollowees() {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * Method to add user tweet to history
	 *
	 * @param user    the user which sends the tweet
	 * @param messageId the message id returned in tweet
	 */
	public void logUserTweet(String user,  String message, int messageId) {

		int currLength = message.length();

		//update length of longest tweet
		if (currLength > lengthOfLongestTweet) lengthOfLongestTweet = currLength;

		HashSet<Integer> messageIdList = new HashSet<Integer>();

		//Update current User Tweet Map
		if (userMessageMap.containsKey(user)) {
			messageIdList = userMessageMap.get(user);
		}
		messageIdList.add(messageId);
		userMessageMap.put(user, messageIdList);
		messageMap.put(messageId, message);



		//Update message Shared Map
		//Share message to all followers of given user
		if(!followHistoryMap.isEmpty() && followHistoryMap.containsKey(user)) {
			HashSet<String> sharedUsers = new HashSet<String>();
			for(String s : followHistoryMap.get(user)){
				sharedUsers.add(s);
				messageSharedHistory.put(messageId, sharedUsers);
			}
		}

		System.out.println("------------------------");
		System.out.println(messageSharedHistory.toString());

		//Update message Shared Map
		//do not Share message to all blockers of given user
		System.out.println("Blocked User Map History" + BlockHistoryMapByUser.toString());
		if(!BlockHistoryMapByUser.isEmpty() &&  BlockHistoryMapByUser.get(user) != null && !BlockHistoryMapByUser.get(user).isEmpty()) {
			HashSet<String> blockedUsers = new HashSet<String>();
			for(String s : BlockHistoryMapByUser.get(user)){
				if( messageSharedHistory.get(messageId) != null && !messageSharedHistory.get(messageId).isEmpty()){
					System.out.println("Removing " + s + "Users List" + messageSharedHistory.get(messageId));
					messageSharedHistory.get(messageId).remove(s);
				}
			}
		}

		//System.out.println(messageSharedHistory.toString());
	}

	/**
	 * Method to log follow History
	 *
	 * @param follower the user which follows other user
	 * @param followee the user which is being followed by other user
	 */
	public void logfollowHistory(String follower, String followee) {

		//Update Follow History Map
		HashSet<String> set = new HashSet<String>();
		if (followHistoryMap.containsKey(followee)) set = followHistoryMap.get(followee);
		set.add(follower);
		followHistoryMap.put(followee, set);
		//	System.out.println("-------- Update Follow History -------------");
	}



}



