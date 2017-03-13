package com.benearley.tweeter.trumpbot;

import com.benearley.tweeter.common.IDFileParser;
import com.benearley.tweeter.common.TweetAccessor;
import com.benearley.tweeter.common.UserTimeline;
import org.junit.Test;
import twitter4j.Status;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.benearley.tweeter.trumpbot.domain.Constants.TRUMP_SCREENNAME;

public class TrumpTimelineTest {

    UserTimeline trumpTimeline = new UserTimeline(TRUMP_SCREENNAME);

    @Test
    public void testTrumpStatuses(){
        long sinceId = TrumpTweeter.getLastProcessed();
        assert sinceId != -1;
        List<Status> statuses = trumpTimeline.getStatusesSince(500, sinceId);
        assert (statuses.size()) == 0;
    }

    @Test
    public void testRecentStatuses(){
        List<Status> statuses = trumpTimeline.getRecentStatuses(19);
        System.out.println(statuses.size());
    }

    @Test
    public void getAllStatuses(){
        List<Status> statuses = trumpTimeline.getAllStatuses();
        System.out.println(statuses.size());
    }

    @Test
    public void getSpecificStatuses() throws IOException {
        File file = new File("src/test/resources/trump_ids.txt");
        List<Status> statusList = TweetAccessor.getStatuses(IDFileParser.getIds(file));
        for (Status status : statusList){
            System.out.println(status.getText());
        }
    }



}
