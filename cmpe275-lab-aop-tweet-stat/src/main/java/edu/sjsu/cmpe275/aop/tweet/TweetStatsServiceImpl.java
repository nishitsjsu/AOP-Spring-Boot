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

	//keep track of messages
	private static HashMap<Integer, String> messageMap = new HashMap<Integer, String>();

	//To keep track of message shared history
	private static TreeMap<Integer, HashSet<String>> messageSharedHistory = new TreeMap<Integer, HashSet<String>>();

	//To keep track of user follow history
	private static TreeMap<String, HashSet<String>> followHistoryMap = new TreeMap<String, HashSet<String>>();

	//To keep track of user block history if A blocks B then this map has list of all who has blocked B
	private static TreeMap<String, HashSet<String>> BlockHistoryMap = new TreeMap<String, HashSet<String>>();

	//To keep track of user block history if A blocks B this map has list who all  A has blocked
	private static TreeMap<String, HashSet<String>> BlockHistoryMapByUser = new TreeMap<String, HashSet<String>>();

	//To keep track of missed tweets by a blocked user
	private static TreeMap<String, Integer> BlockedUserMissedTweets = new TreeMap<String, Integer>();

	//To keep track of most blocked follower
	private static TreeMap<String, HashSet<String>> MostBlockedFollower = new TreeMap<String, HashSet<String>>();

	@Override
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
		lengthOfLongestTweet = 0;
		BlockHistoryMap =  new TreeMap<String, HashSet<String>>();
		userMessageMap = new TreeMap<String, HashSet<Integer>>();
		messageSharedHistory = new TreeMap<Integer, HashSet<String>>();
		BlockedUserMissedTweets = new TreeMap<String, Integer>();
		followHistoryMap = new TreeMap<String, HashSet<String>>();
		MostBlockedFollower = new TreeMap<String, HashSet<String>>();
		messageMap = new HashMap<Integer, String>();
		BlockHistoryMapByUser = new TreeMap<String, HashSet<String>>();
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		// TODO Auto-generated method stub
		String productiveUser = null;
		Integer maxValue = Integer.MIN_VALUE;
		for(Map.Entry<String,HashSet<String>> entry : followHistoryMap.entrySet()) {
			String key = entry.getKey();
			if(entry.getValue().size() > maxValue)
			{
				maxValue = entry.getValue().size();
				productiveUser = entry.getKey();
			}
			System.out.println(key + " => " + entry.getValue().toString());
		}
		return productiveUser;
	}

	@Override
	public String getMostPopularMessage() {
		// TODO Auto-generated method stub
		TreeMap<String, HashSet<String>> popularityComparision = new TreeMap<String, HashSet<String>>();

		Integer maxMessage = Integer.MIN_VALUE;
		String popularMessage = null;
		for(Map.Entry<Integer, HashSet<String>> entry : messageSharedHistory.entrySet()){
			if(entry.getValue().size() > maxMessage) {
				maxMessage = entry.getValue().size();
			}
		}

		for(Map.Entry<Integer, HashSet<String>> entry : messageSharedHistory.entrySet()){
			if(entry.getValue().size() ==  maxMessage) {
				maxMessage = entry.getValue().size();
				popularityComparision.put(messageMap.get(entry.getKey()), entry.getValue());
			}
		}

		System.out.println(messageSharedHistory.toString());
		System.out.println("Popularoty Map" + popularityComparision.toString());

		for(Map.Entry<String, HashSet<String>> entry : popularityComparision.entrySet()){
			if(!entry.getValue().isEmpty()) {
				popularMessage = entry.getKey();
				break;
			}
		}
		return  popularMessage;
	}
	
	@Override
	public String getMostProductiveUser() {
		// TODO Auto-generated method stub
		String productiveUser = null;
		Integer maxMessageValue = Integer.MIN_VALUE;

		Map<String, Integer> messageLengthMap = new TreeMap<String, Integer>();

		for(Map.Entry<String, HashSet<Integer>> entry : userMessageMap.entrySet()){
			Integer messageLength = 0;
			for(Integer i : entry.getValue()) {
				messageLength += messageMap.get(i).length();
			}
			messageLengthMap.put(entry.getKey(), messageLength);
		}

		for(Map.Entry<String, Integer> entry : messageLengthMap.entrySet()) {
			if(entry.getValue() > maxMessageValue) {
				maxMessageValue = entry.getValue();
				productiveUser = entry.getKey();
			}
		}
		return productiveUser;
	}

	@Override
	public String getMostBlockedFollowerByNumberOfMissedTweets() {
		// TODO Auto-generated method stub
		int ma =0;
		String out = "";
		if (BlockedUserMissedTweets.isEmpty()){
			return null;
		} else {
			out = BlockedUserMissedTweets.firstKey();
			for (Map.Entry<String, Integer> s : BlockedUserMissedTweets.entrySet()) {
				if (s.getValue() > ma) {
					ma = s.getValue();
					out = s.getKey();
				}
			}
			return out;
		}
	}

	@Override
	public String getMostBlockedFollowerByNumberOfFollowees() {
		// TODO Auto-generated method stub
		int ma =0;
		HashSet<String> set = new HashSet<String>();
		String out = "";
		if (MostBlockedFollower.isEmpty()){
			return null;
		} else {
//			out = MostBlockedFollower.firstKey();
			for (Map.Entry<String, HashSet<String>> s : MostBlockedFollower.entrySet()) {
				set = s.getValue();
				if(set.size() > ma){
					ma = set.size();
					out = s.getKey();
				}
			}
			if(out!=""){
				return out;
			} else{
				return null;
			}
		}
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

		//Share message to all followers of given user
		if(!followHistoryMap.isEmpty() && followHistoryMap.containsKey(user)) {
			HashSet<String> sharedUsers = new HashSet<String>();
			for(String s : followHistoryMap.get(user)){
				sharedUsers.add(s);
				messageSharedHistory.put(messageId, sharedUsers);
			}
		}

//		System.out.println(messageSharedHistory.toString());

		//do not Share message to all blockers of given user
		System.out.println("Blocked User Map History" + BlockHistoryMapByUser.toString());
		System.out.println("Blocked Map History" + BlockHistoryMap.toString());
		System.out.println("Blocked User Missed tweets" + BlockedUserMissedTweets.toString());
		System.out.println("follow history" + followHistoryMap.toString());
		System.out.println("Most blocked follower history" + MostBlockedFollower.toString());

		if(!BlockHistoryMapByUser.isEmpty() &&  BlockHistoryMapByUser.get(user) != null && !BlockHistoryMapByUser.get(user).isEmpty()) {
			HashSet<String> blockedUsers = new HashSet<String>();
			for(String s : BlockHistoryMapByUser.get(user)){
				if( messageSharedHistory.get(messageId) != null && !messageSharedHistory.get(messageId).isEmpty()){

					if(messageSharedHistory.get(messageId).contains(s)){
						//incrementing missed tweets value
						System.out.println("Adding missed tweet for " + s );
						int val = BlockedUserMissedTweets.get(s);
						val = val + 1;
						BlockedUserMissedTweets.put(s,val);
					}
					System.out.println("Removing " + s + " from Users List" + messageSharedHistory.get(messageId));
					messageSharedHistory.get(messageId).remove(s);

				}
			}
		}

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

	/**
	 * Method to log Block History
	 *
	 */
	public void logBlockHistory(String user, String followee) {

		//To keep track of user block history if A blocks B this map has list who all has blocked B
		HashSet<String> set = new HashSet<String>();
		if (BlockHistoryMap.containsKey(followee)) {
			set = BlockHistoryMap.get(followee);
		} else {
			BlockedUserMissedTweets.put(followee,0);
		}
		set.add(user);
		BlockHistoryMap.put(followee, set);

		//To keep track of user block history if A blocks B this map has list who all  A has blocked
		HashSet<String> blockedset = new HashSet<String>();
		if (BlockHistoryMapByUser.containsKey(user))
			blockedset = BlockHistoryMapByUser.get(user);
		blockedset.add(followee);
		BlockHistoryMapByUser.put(user, blockedset);

		//To keep track of most blocked follower
		HashSet<String> setF = new HashSet<String>();
		HashSet<String> setTemp = new HashSet<String>();
		if (MostBlockedFollower.containsKey(followee)) {
			setF = MostBlockedFollower.get(followee);
		}
		if (followHistoryMap.containsKey(user)){
			setTemp = followHistoryMap.get(user);
			if (setTemp.contains(followee)){
				setF.add(user);
			}
		}
		MostBlockedFollower.put(followee,setF);

	}

	public void logUnBlockHistory(String user, String followee) {

		HashSet<String> set = new HashSet<String>();
		if (BlockHistoryMap.containsKey(followee)) set = BlockHistoryMap.get(followee);
		set.remove(user);
		BlockHistoryMap.put(followee, set);

		HashSet<String> blockedset = new HashSet<String>();
		if (BlockHistoryMapByUser.containsKey(user))
			blockedset = BlockHistoryMapByUser.get(user);
		blockedset.remove(followee);
		BlockHistoryMapByUser.put(user, blockedset);

		//Removing from most blocked follower
		HashSet<String> blockedsetfollower = new HashSet<String>();
		if (MostBlockedFollower.containsKey(followee))
			blockedsetfollower = MostBlockedFollower.get(followee);
		blockedsetfollower.remove(user);
		MostBlockedFollower.put(followee, blockedsetfollower);
	}

}



