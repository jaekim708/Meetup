package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;
import com.parse.ParseObject;

public class MainActivity extends Activity {
    ParseObject activities = new ParseObject("activities");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
