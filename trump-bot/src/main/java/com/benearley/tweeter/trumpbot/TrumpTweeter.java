package com.benearley.tweeter.trumpbot;

import org.apache.log4j.Logger;
import twitter4j.*;

import java.util.*;

import static com.benearley.tweeter.trumpbot.domain.Constants.ANDROID;
import static com.benearley.tweeter.trumpbot.domain.Constants.IPHONE;
import static com.benearley.tweeter.trumpbot.domain.Constants.TRUMP_BOT_SCREENNAME;

/**
 * Created by 578803 on 1/30/2017.
 */
public class TrumpTweeter {

    static Twitter twitter;
    static Random random;
    static Logger logger = Logger.getLogger(TrumpTweeter.class);

    static {
        twitter = TwitterFactory.getSingleton();
        random = new Random();
    }

    public static void updateStatus(Status s) {
        try {
            long id = s.getId();
            String source = wasTrump(s);
            if (!source.isEmpty()) {
                StatusUpdate statusUpdate;
                if (wasTrump(s).equals(ANDROID)) {
                    statusUpdate = new StatusUpdate("This Tweet was sent from an Android Phone. Most likely sent by President Trump in a fit of rage.\n" +
                            "https://twitter.com/realDonaldTrump/status/" + id);

                } else {
                    statusUpdate = new StatusUpdate("This Tweet was sent from an iPhone. Most likely sent by Trump's aides doing damage control.\n" +
                            "https://twitter.com/realDonaldTrump/status/" + id);
                }
                statusUpdate.inReplyToStatusId(id);
                twitter.updateStatus(statusUpdate);
                logger.info("Updated Status from Trump Tweet at: " + s.getCreatedAt());
                Thread.sleep(random.nextInt(10000));
            }
        } catch (TwitterException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatuses(Map<Long, Status> idLongs) {
        for (Map.Entry<Long, Status> entry : idLongs.entrySet()) {
            updateStatus(entry.getValue());
        }

    }

    private static String wasTrump(Status status) {
        if (status.getSource().contains(ANDROID)) return ANDROID;
        else if (status.getSource().contains(IPHONE)) return IPHONE;
        else return "";
    }


    public static SortedMap<Long, Status> getCompleteStatuses() {
        List<Status> statuses = new ArrayList<>();
        SortedMap<Long, Status> statusIds = new TreeMap<>();
        try {
            statuses = twitter.getUserTimeline(TRUMP_BOT_SCREENNAME);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        statuses.forEach(s -> statusIds.put(s.getQuotedStatusId(), s));
        return statusIds;
    }

    public static long getLastProcessed(){
        Paging paging = new Paging();
        paging.setCount(1);
        List<Status> statuses = new ArrayList<>();
        try {
            statuses.addAll(twitter.getUserTimeline(TRUMP_BOT_SCREENNAME, paging));
        } catch (TwitterException e) {
            e.printStackTrace();
            return -1;
        }
        if (statuses.isEmpty()) return -1;
        else return statuses.get(0).getQuotedStatusId();
    }
}
