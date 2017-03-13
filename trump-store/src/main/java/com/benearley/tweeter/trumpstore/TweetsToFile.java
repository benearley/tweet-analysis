package com.benearley.tweeter.trumpstore;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.benearley.tweeter.common.IDFileParser;
import com.benearley.tweeter.common.TweetAccessor;
import com.benearley.tweeter.trumpstore.dynamo.DynamoConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import twitter4j.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.benearley.tweeter.trumpstore.domain.Constants.ID_FILE;
import static com.benearley.tweeter.trumpstore.domain.Constants.TRUMP_DYNAMO_TABLE;

public class TweetsToFile {

    public static void main(String[] args) throws InterruptedException {
        List<Long> ids = IDFileParser.getIds(new File(ID_FILE));
        Table table = DynamoConnection.getTable(TRUMP_DYNAMO_TABLE);
        Gson gson = new Gson();

        boolean skipped = false;
        List<Status> statuses = new ArrayList<>();
        for (int lowerBound = 0; lowerBound < ids.size(); lowerBound += 99) {
            if (!skipped && lowerBound > 99 * 250) {
                System.out.println("sleeping ");
                Thread.sleep(1000 * 60 * 15L);
                skipped = true;
            }
            System.out.println(lowerBound + " tweets processed");
            int upperBound = lowerBound + 99;
            if (upperBound > ids.size()) {
                upperBound = ids.size();
            }
            List<Long> subList = ids.subList(lowerBound, upperBound);
            statuses.addAll(TweetAccessor.getStatuses(subList));
        }

        System.out.println("FINISHED ACCESSING STATUSES");

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("trumptweets.json"));
            for (Status status : statuses){
                bw.write(gson.toJson(status)+"\n");
            }
            bw.close();
        } catch (IOException e){
            System.out.println("Can't write to file");
        }

        System.out.println("STATUSES WRITTEN TO FILE");

//        for (Status status : statuses) {
//            String statusJSON = gson.toJson(status);
//            statusJSON = statusJSON.replaceAll("\"\"","N/A");
//            if (!status.isRetweet() && status.getUser().getName().equals("")) {
//                Item item = new Item().withPrimaryKey("id", String.valueOf(status.getId())).withJSON("document", statusJSON);
//                try{
//                    table.putItem(item);
//                } catch (Exception e){
//                    System.out.println(gson.toJson(status));
//                    System.out.println("DIDN'T WORK");
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
