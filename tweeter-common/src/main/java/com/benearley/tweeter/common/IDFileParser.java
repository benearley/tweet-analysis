package com.benearley.tweeter.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class IDFileParser {

    public static List<Long> getIds(File file) {
        List<Long> ids = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                ids.add(Long.parseLong(line));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ids;
    }
}
