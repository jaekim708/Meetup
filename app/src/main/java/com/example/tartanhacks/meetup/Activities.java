package com.example.tartanhacks.meetup;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Activities")
public class Activities extends ParseObject {
    public String getText() {
        return getString("text");
    }

    public void setText(String value) {
        put("text", value);
    }



    public static ParseQuery<Activities> getQuery() {
        return ParseQuery.getQuery(Activities.class);
    }
}