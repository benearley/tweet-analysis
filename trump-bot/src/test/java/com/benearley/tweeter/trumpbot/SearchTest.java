package com.benearley.tweeter.trumpbot;

import com.google.common.collect.Lists;
import org.junit.Test;
import twitter4j.*;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by 578803 on 3/6/2017.
 */
public class SearchTest {

    Twitter twitter = TwitterFactory.getSingleton();

    @Test
    public void test() throws TwitterException {
        List<Status> totalTweets = Lists.newArrayList();
        Query query = new Query("from:realDonaldTrump Tucson");
        query.setCount(100);
        query.setMaxId(837633820417482754L);
        QueryResult result = twitter.search(query);
        totalTweets.addAll(result.getTweets());
        System.out.println(result.getTweets().size());
        System.out.println(totalTweets.stream().mapToLong(Status::getId).min().getAsLong());
        for (Status s : totalTweets){
            System.out.println(s.getText());
        }

        twitter.lookup(597717624978571264L);



    }

    @Test
    public void lookupTest() throws TwitterException {
        List<Status> statuses = twitter.lookup(597717624978571264L);

    }

    @Test
    public void urlTest() throws IOException {
        URL myURL = new URL("http://www.trumptwitterarchive.com/archive/none/ttff/_3-20-2016");
        URLConnection myURLConnection = myURL.openConnection();
        myURLConnection.connect();
        File file = new File("C:\\Users\\578803\\Documents\\trump.html");
        FileWriter writer = new FileWriter(file);
        BufferedReader in = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            writer.write(inputLine);
        in.close();


    }

}
