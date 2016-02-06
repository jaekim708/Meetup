package com.example.tartanhacks.meetup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;

import com.parse.ParseObject;

import org.w3c.dom.Text;

public class ProposeActivity extends AppCompatActivity {

    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void sendParseData(View v)   {
        // get EditText by id
        EditText grabName = (EditText) findViewById(R.id.activity_name);
        // Store EditText in Variable
        String activityName = grabName.getText().toString();

        EditText grabNum = (EditText) findViewById(R.id.number_people);
        //int numPeople = Integer.parseInt(grabNum.getText().toString());
        String numPeopleStr = grabNum.getText().toString();

        if(TextUtils.isEmpty(activityName) && TextUtils.isEmpty(numPeopleStr)) {
            grabName.setError("Please enter an activity name.");
            grabNum.setError("Please specify the number of people.");
            return;
        }
        if(TextUtils.isEmpty(activityName)) {
            grabName.setError("Please enter an activity name.");
            return;
        }
        if(TextUtils.isEmpty(numPeopleStr)) {
            grabNum.setError("Please specify the number of people.");
            return;
        }

        int numPeople = Integer.parseInt(grabNum.getText().toString());

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        String activityType = spinner.getSelectedItem().toString();

        UserActivity userActivity = new UserActivity();
        userActivity.put("name", activityName);
        userActivity.put("numOfPeople", numPeople);
        userActivity.put("category", activityType);
        userActivity.saveInBackground();

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}