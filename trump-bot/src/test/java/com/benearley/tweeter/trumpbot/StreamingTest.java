package com.benearley.tweeter.trumpbot;


import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 578803 on 1/30/2017.
 */
public class StreamingTest {

    @Test
    public void test() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);

        List<Long> userIds = Lists.newArrayList(25073877L);

        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.followings(userIds);

        String consumerKey = "Nz3534lpuzRnR9iscba26Z4Jk";
        String consumerSecret = "eQPEC0kxXnRshVMfCh2tj0AUxA10pRvw6mY55THAjCl0xLv16w";
        String token = "826140224396861440-bz0Vt5qG3FoLpyKlUelW08kkKg3Em2E";
        String secret = "VPUGfVchgcyGfr1SnTUlUWtjzICn6XdtrQUWUwMmDMgzw";

        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);

        // Create a new BasicClient. By default gzip is enabled.
        BasicClient client = new ClientBuilder()
                .name("sampleExampleClient")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(auth)
                .processor(new StringDelimitedProcessor(queue))
                .build();

        client.connect();

        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 5; msgRead++) {
            String msg = queue.take();
            System.out.println(msg);
        }

        client.stop();



    }

}
