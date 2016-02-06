package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends Activity {
    ParseQuery<ParseObject> activities_query = ParseQuery.getQuery("UserActivity");
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

    /* This will be called when the user clicks the "Propose" button. */
    public void proposeActivity(View view)  {
        Intent intent = new Intent(this, ProposeActivity.class);
        startActivity(intent);
    }
}
