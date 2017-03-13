package com.benearley.tweeter.trumpbot;

import com.benearley.tweeter.common.UserTimeline;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import twitter4j.Status;

import java.util.List;
import java.util.SortedMap;

import static com.benearley.tweeter.trumpbot.domain.Constants.TRUMP_SCREENNAME;

public class App {

    private static Logger logger = Logger.getLogger(App.class);
    private static UserTimeline trumpTimeline = new UserTimeline(TRUMP_SCREENNAME);

    public static void run(SortedMap<Long,Status> statusIds) {

        while (true) {
            logger.info("Receiving Trump Timeline");
            List<Status> recentIds = trumpTimeline.getRecentStatuses(19);
            SortedMap<Long,Status> recentMap = Maps.newTreeMap();
            recentIds.forEach(i -> recentMap.put(i.getId(),i));

            for (Status status : recentMap.values()) {
                if (!statusIds.containsKey(status.getId())) {
                    logger.info("Found new status: " + new DateTime());
                    TrumpTweeter.updateStatus(status);
                    statusIds.put(status.getId(), status);
                    if (statusIds.size() > 100) {
                        statusIds.remove(statusIds.firstKey());
                    }
                }
            }

            try {
                Thread.sleep(3*60*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SortedMap<Long,Status> statusIds = Maps.newTreeMap();
        long lastProcessed = TrumpTweeter.getLastProcessed();
        // If command line parameter is passed, process the last x amount of tweets
        if (args != null && args.length > 0){
            try {
                int count = Integer.parseInt(args[0]);
                statusIds = BackFiller.runBackFill(count,lastProcessed);
            } catch (Exception e){
                System.out.println("Specify amount to backfill with first argument (must be integer)");
            }
        }
        //find which tweets have already been processed by the bot
        logger.info("Get Statuses already complete");
        statusIds.putAll(TrumpTweeter.getCompleteStatuses());

        run(statusIds);
    }
}
