package com.benearley.tweeter.trumpbot;

import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by 578803 on 1/30/2017.
 */
public class CompleteStatusTest {

    Gson gson = new Gson();

    @Test
    public void test(){
        TrumpTweeter.getCompleteStatuses().forEach((a, b) -> System.out.println(gson.toJson(b)));
    }

    @Test
    public void testLastProcessed(){
        System.out.println(TrumpTweeter.getLastProcessed());
    }

}
