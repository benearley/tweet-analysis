package com.benearley.tweeter.common;

import org.junit.Test;

/**
 * Created by 578803 on 3/7/2017.
 */
public class TwitterTest {

    private static final long statusId = 838899465390018560L;
    @Test
    public void getOneStatus(){
        assert TweetAccessor.getStatus(statusId).getText().contains("JOBS");
    }
}
