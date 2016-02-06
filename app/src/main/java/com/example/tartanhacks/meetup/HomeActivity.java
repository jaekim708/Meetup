package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;

import com.parse.ParseUser;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /* This will be called when the user clicks the "Propose" button. */
    public void proposeActivity(View view)  {
        Intent intent = new Intent(this, ProposeActivity.class);
        startActivity(intent);
    }
    public void logout(View view)  {
        ParseUser user = ParseUser.getCurrentUser();
        user.logOut();
    }
    public void searchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
