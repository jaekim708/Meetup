package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.util.List;

public class SwipeActivity extends Activity {
    ParseQuery<UserActivity> activities_query;
    CardContainer mCardCont;
    SimpleCardStackAdapter adapter;
    boolean userDislikes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);

        activities_query = ParseQuery.getQuery(UserActivity.class);
        mCardCont = (CardContainer) findViewById(R.id.cardCont);
        adapter = new SimpleCardStackAdapter(this);
        userDislikes = false;

        activities_query.findInBackground(new FindCallback<UserActivity>() {
            public void done(List<UserActivity> uActivities, ParseException e) {
                if (e == null) {
                    for (UserActivity a : uActivities) {
                        String activityName = a.getName();

                        ParseRelation relation = a.getRelation("usersDisliked");
                        ParseQuery dislikeQuery = relation.getQuery();
                        dislikeQuery.whereEqualTo("getUsersDisliked", ParseUser.getCurrentUser());
                        userDislikes = false;

                        dislikeQuery.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> dislikedUsers,
                                             ParseException e) {
                                if (e == null) {
                                    if (dislikedUsers.size() > 1){
                                        userDislikes = true;
                                    }
                                } else {
                                    //shouldn't be here?
                                }
                            }
                        });

                        if (userDislikes)
                            continue;


                        System.out.println("%d" + a.getNumOfPeople());
                        CardModel card = new CardModel(activityName,
                                "Description goes here",
                                ContextCompat.getDrawable(getApplicationContext(),
                                        R.drawable.picture1));
                        card.setOnClickListener(new CardModel.OnClickListener() {
                            @Override
                            public void OnClickListener() {
                                System.out.println("Swipeable Cards" + " DISPLAY MORE INFO");
                            }
                        });

                        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                            @Override
                            public void onLike() {
                                userDislikes = false;
                                System.out.println("Swipeable Cards" + " I like the card");
                            }

                            @Override
                            public void onDislike() {
                                userDislikes = true;
                                System.out.println("Swipeable Cards" + "I dislike the card");
                            }
                        });
                        if (userDislikes)
                            a.addUsersDisliked(ParseUser.getCurrentUser());
                        else
                            a.addUsersLiked(ParseUser.getCurrentUser());
                        adapter.add(card);
                        a.saveInBackground();
                    }
                    mCardCont.setAdapter(adapter);
                } else {
                    // shouldn't be here...
                }
            }
        });
    }
}
