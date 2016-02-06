package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class SwipeActivity extends Activity {
    ParseQuery<UserActivity> activities_query = ParseQuery.getQuery(UserActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activities_query.findInBackground(new FindCallback<UserActivity>() {
            public void done(List<UserActivity> uActivities, ParseException e) {
                if (e == null) {
                    for (UserActivity a : uActivities) {
                        System.out.println("%d" + a.getNumOfPeople());

                    }
                } else {
                    // shouldn't be here...
                }
            }
        });
    }
}
