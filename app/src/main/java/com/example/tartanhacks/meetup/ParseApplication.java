package com.example.tartanhacks.meetup;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by jae on 2/6/16.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(UserActivity.class);
        Parse.initialize(this, this.getString(R.string.parse_app_id),
                this.getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(getApplicationContext());
    }

}
