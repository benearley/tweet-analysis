package com.benearley.tweeter.common;

import org.apache.log4j.Logger;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.List;

public class TweetAccessor {

    private static Logger logger = Logger.getLogger(TweetAccessor.class);
    private static Twitter twitter = TwitterFactory.getSingleton();

    public static Status getStatus(long id){
        Status status = null;
        try {
            status = twitter.showStatus(id);
        } catch (TwitterException e) {
            logger.error("Can't receive tweet with id: " + id);
        }
        return status;
    }

    public static List<Status> getStatuses(List<Long> ids){
        long[] idInput = ids.stream().mapToLong(l -> l).toArray();
        List<Status> statuses = new ArrayList<>();
        try {
            statuses = twitter.lookup(idInput);
        } catch (TwitterException e) {
            logger.error("Can't receive statuses");
        }
        return statuses;
    }
}
