package com.example.tartanhacks.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends Activity {
    private String email;
    private String name;
    private String displayName;
    private String ageRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        getUserDetailsFromFB();
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void setNewUserInfo() {
        ParseUser user = ParseUser.getCurrentUser();
        user.put("email", email);
        user.put("name", name);
        user.put("displayName", displayName);
        user.put("ageRange", ageRange);

        user.saveInBackground();
    }

    private void getUserDetailsFromFB() {
        // Suggested by https://disqus.com/by/dominiquecanlas/
        Log.d("MyApp", "Sending request to facebook.");

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,first_name,age_range,picture");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("MyApp", "Received response from facebook.");

         /* handle the result */
                        try {
                            email = response.getJSONObject().getString("email");
                            name = response.getJSONObject().getString("name");
                            displayName = response.getJSONObject().getString("first_name");
                            ageRange = response.getJSONObject().getString("age_range");
                            //mEmailID.setText(email);
                            //mUsername.setText(name);
                            JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");
                            //  Returns a 50x50 profile picture
                            // String pictureUrl = data.getString("url");
                            // new ProfilePhotoAsync(pictureUrl).execute();

                            setNewUserInfo();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

}
