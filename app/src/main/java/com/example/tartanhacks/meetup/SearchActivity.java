package com.example.tartanhacks.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    Intent intent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_outdoor:
                /*
                if (checked)
                // Do what when checked?
                else
                // If not checked?
                */
                break;
            case R.id.checkbox_movies:
                /*
                if (checked)
                // Do what when checked?
                else
                // If not checked?
                */
                break;
            case R.id.checkbox_shopping:
                /*
                if (checked)
                // Do what when checked?
                else
                */
                // If not checked?
                break;
            case R.id.checkbox_sports:
                /*
                if (checked)
                // Do what when checked?
                else
                // If not checked?
                */
                break;
        }
    }
    public void submitSearch(View v) {
        //EditText getSearch = (EditText) findViewById(R.id.search_query);
        //String searchQuery = getSearch.getText().toString();

        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }
}
