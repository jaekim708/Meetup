package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;
import com.parse.ParseObject;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    ParseObject activities = new ParseObject("activities");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* This will be called when the user clicks the "Propose" button. */
    public void proposeActivity(View view)  {
        //do something
        Intent intent = new Intent(this, ProposeActivity.class);
        startActivity(intent);
    }
}
