package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class SwipeActivity extends Activity {
    ParseQuery activities_query = UserActivity.getQuery();
    ParseObject activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activities_query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    activities = objects.get(0); // should only be one UserActivity object
                } else {
                    // shouldn't be here...
                }
            }
        });
    }
}
