package com.benearley.tweeter.trumpstore;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import twitter4j.Status;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 578803 on 3/8/2017.
 */
public class LoadTweetsFromJson {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Status>>() {}.getType();
        List<Status> statuses = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("trumptweets.json"))) {
            String statusString = br.readLine();
            Status status = gson.fromJson(statusString,Status.class);

            //TODO Write status to DB

        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("it work in this many seconds: " + (end-start)/1000.0);

    }
}
