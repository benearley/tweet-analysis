package com.benearley.tweeter.common;

import org.apache.log4j.Logger;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class UserTimeline {

    static Logger logger = Logger.getLogger(UserTimeline.class);
    static Twitter twitter = TwitterFactory.getSingleton();
    private String screenname;

    public UserTimeline(String screenname) {
        this.screenname = screenname;
    }

    public List<Status> getStatusesSince(int totalCount, long sinceId) {
        return getStatuses(totalCount,-1,sinceId);
    }

    public List<Status> getRecentStatuses(int count){
        return getStatuses(count, -1, -1);
    }

    public List<Status> getAllStatuses(){
        return getStatuses(3210,-1, -1);
    }

    public List<Status> getStatuses(int count, long maxId, long sinceId){
        int countLeft = (count == -1) ? 200 : count;
        List<Status> statuses = new ArrayList<>();
        Paging paging = new Paging();
        while (countLeft > 0) {
            if (maxId != -1 && sinceId != -1 && sinceId > maxId) break;
            if (maxId != -1) paging.setMaxId(maxId);
            if (sinceId != -1) paging.setSinceId(sinceId);
            paging.setCount(countLeft);
            try {
                ResponseList<Status> returnedStatuses = twitter.getUserTimeline(screenname, paging);
                statuses.addAll(returnedStatuses);
            } catch (TwitterException e) {
                e.printStackTrace();
                return null;
            }
            countLeft -= 199;
            if (statuses.size() == 0){
                break;
            }
            maxId = statuses.stream().mapToLong(Status::getId).min().getAsLong();
        }
        return statuses;
    }

}
