package com.benearley.tweeter.trumpbot;

import com.benearley.tweeter.common.UserTimeline;
import twitter4j.Status;

import java.util.*;

import static com.benearley.tweeter.trumpbot.domain.Constants.TRUMP_SCREENNAME;

/**
 * Created by 578803 on 1/30/2017.
 */
public class BackFiller {

    private static final UserTimeline trumpTimeline = new UserTimeline(TRUMP_SCREENNAME);

    public static SortedMap<Long,Status> runBackFill(int totalCount, long sinceId){
        SortedMap<Long,Status> idLongs = new TreeMap<>();

        List<Status> statuses = trumpTimeline.getStatusesSince(totalCount,sinceId);
        statuses.forEach(s -> idLongs.put(s.getId(),s));

        TrumpTweeter.updateStatuses(idLongs);

        System.out.println("Total Statuses: " + idLongs.size());
        return idLongs;
    }
}
