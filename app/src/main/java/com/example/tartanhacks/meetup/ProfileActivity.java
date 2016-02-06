package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ProfileActivity extends Activity {
    private String email;
    private String name;
    private String displayName;
    private TextView nameView;
    private TextView bioView;
    private CircularImageView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameView = (TextView) findViewById(R.id.name);
        bioView = (TextView) findViewById(R.id.bio);
        profilePictureView = (CircularImageView) findViewById(R.id.profile_picture);

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
        user.put("displayName", displayName);
        user.put("bio", bioView.getText().toString());

        user.saveInBackground();
    }

    private void getUserDetailsFromFB() {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,first_name,picture.width(300).height(300)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me", parameters, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        try {
                            email = response.getJSONObject().getString("email");
                            name = response.getJSONObject().getString("name");
                            displayName = response.getJSONObject().getString("first_name");
                            nameView.setText(name);

                            JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");
                            //  Returns a 50x50 profile picture
                            String pictureUrl = data.getString("url");
                            new ProfilePhotoAsync(pictureUrl).execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    class ProfilePhotoAsync extends AsyncTask<String, String, String> {
        public Bitmap bitmap;
        String url;
        public ProfilePhotoAsync(String url) {
            this.url = url;
        }
        @Override
        protected String doInBackground(String... params) {
            // Fetching data from URI and storing in bitmap
            bitmap = DownloadImageBitmap(url);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            profilePictureView.setImageBitmap(bitmap);
        }

    }

    public static Bitmap DownloadImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            Log.d("IMG", "Image successfully downloaded: " + bm.getByteCount());
        } catch (IOException e) {
            Log.e("IMAGE", "Error getting bitmap", e);
        }
        return bm;
    }

}
