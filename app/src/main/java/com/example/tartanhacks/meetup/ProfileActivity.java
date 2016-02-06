package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.ParseUser;

import org.json.JSONException;

public class ProfileActivity extends Activity {
    private String email;
    private String name;
    private TextView displayNameView;
    private TextView bioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        displayNameView = (TextView) findViewById(R.id.display_name);
        bioView = (TextView) findViewById(R.id.bio);

        getUserDetailsFromFB();
    }

    public void goHome(View view) {
        setNewUserInfo();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void setNewUserInfo() {
        ParseUser user = ParseUser.getCurrentUser();
        user.setEmail(email);
        user.put("name", name);
        user.put("displayName", displayNameView.getText().toString());
        user.put("bio", bioView.getText().toString());

        user.saveInBackground();
    }

    private void getUserDetailsFromFB() {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,first_name,picture");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", parameters, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            email = response.getJSONObject().getString("email");
                            name = response.getJSONObject().getString("name");
                            displayNameView.setText(response.getJSONObject().getString("first_name"));

                            //mEmailID.setText(email);
                            //mUsername.setText(name);
                            //JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            // JSONObject data = picture.getJSONObject("data");
                            //  Returns a 50x50 profile picture
                            // String pictureUrl = data.getString("url");
                            // new ProfilePhotoAsync(pictureUrl).execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

}
