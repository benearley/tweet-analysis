package com.benearley.tweeter.trumpstore;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.benearley.tweeter.common.IDFileParser;
import com.benearley.tweeter.common.TweetAccessor;
import com.benearley.tweeter.trumpstore.dynamo.DynamoConnection;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import twitter4j.Status;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.benearley.tweeter.trumpstore.domain.Constants.TRUMP_DYNAMO_TABLE;

public class DynamoConnectionTest {

    private Table table;
    private Gson gson = new Gson();
    private static List<Long> ids;

    @Before
    public void setup() throws IOException {
        table = DynamoConnection.getTable(TRUMP_DYNAMO_TABLE);
        ids = IDFileParser.getIds(new File("src/test/resources/trump_ids.txt"));
    }

    @Test
    public void testPut() throws IOException {
        List<Status> statusList = TweetAccessor.getStatuses(ids);
        for (Status status : statusList) {
            Item item = new Item().withPrimaryKey("id", String.valueOf(status.getId())).withJSON("document", gson.toJson(status));
            table.putItem(item);
        }
    }

    @Test
    public void testGet(){
        for (Long id : ids) {
            Item docItem = table.getItem(new GetItemSpec()
                    .withPrimaryKey("id", String.valueOf(id))
                    .withAttributesToGet("document"));
            if (docItem != null) {
                System.out.println(docItem.getJSON("document"));
            } else {
                System.out.println(id + " is null");
            }
        }
    }

    @Test
    public void testPutEmptyString(){

    }

    @After
    public void testDelete(){

    }

}
