package com.benearley.tweeter.trumpbot;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.SortedSet;

/**
 * Created by 578803 on 1/30/2017.
 */
public class SortedSetTest {

    @Test
    public void test(){
        SortedSet<Long> finishedIds = Sets.newTreeSet();
        for (long l = 0; l < 20; l++){
            finishedIds.add(l);
        }

        while (!finishedIds.isEmpty()){
            System.out.println(finishedIds.last());
            finishedIds.remove(finishedIds.last());
        }


    }


}
