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

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends Activity {
    ParseQuery<UserActivity> activities_query = ParseQuery.getQuery(UserActivity.class);
    CardContainer mCardCont;
    SimpleCardStackAdapter adapter;
    boolean userSeen;
    boolean queryDone;
    ArrayList<String> currCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);
        mCardCont = (CardContainer) findViewById(R.id.cardCont);
        adapter = new SimpleCardStackAdapter(this);
        currCards = new ArrayList<>();
        showCards();
    }

    public void updateCards() {
        activities_query.findInBackground(new FindCallback<UserActivity>() {
            public void done(List<UserActivity> uActivities, ParseException e) {
                if (e == null) {
                    for (final UserActivity a : uActivities) {
                        String activityName = a.getName();
                        final String activityID = a.getObjectId();
                        ParseRelation<ParseUser> relation = a.getUsersSeen();
                        ParseQuery seenQuery = relation.getQuery();
                        seenQuery.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                        queryDone = false;
                        userSeen = false;
                        try {
                            int n = seenQuery.find().size();
                            if (seenQuery.find().size() > 0) {
                                System.out.println("userSeen is true " + n);
                                userSeen = true;
                            }
                        }
                        catch (ParseException E) {}
                        System.out.println("userseen is " + userSeen);
                        if (userSeen || currCards.contains(activityID)) {
                            System.out.println("NOT ADDING " + activityID);
                            continue;
                        } else {
                            System.out.println("ADDING " + activityID);
                            for(int i = 0; i < currCards.size(); i++)
                                System.out.println(currCards.get(i));
                        }
                        currCards.add(activityID);
                        a.addUsersSeen(ParseUser.getCurrentUser());
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
                            public void onLike() { // swiping LEFT
                                userSeen = true;
                                System.out.println("Swipeable Cards" + " I dislike the card");
                                a.setName("I DON'T LIKE IT");
                                a.saveInBackground();
                                currCards.remove(activityID);
                                for (int i = 0; i < currCards.size(); i++) {
                                    System.out.println(currCards.get(i) + activityID);
                                }
                                adapter.pop();
                                updateCards();
                                a.addUsersSeen(ParseUser.getCurrentUser());
                            }

                            @Override
                            public void onDislike() { // swiping RIGHT
                                userSeen = true;
                                System.out.println("Swipeable Cards" + "I like the card");
                                a.setName("I LIKE IT");
                                a.saveInBackground();
                                currCards.remove(activityID);
                                for (int i = 0; i < currCards.size(); i++) {
                                    System.out.println(currCards.get(i) + activityID);
                                }
                                adapter.pop();
                                updateCards();
                                a.addUsersSeen(ParseUser.getCurrentUser());
                            }
                        });
                        adapter.add(card);

                        mCardCont.setAdapter(adapter);
                        a.saveInBackground();
                    }
                }
            }
        });
    }

    public void showCards() {
        updateCards();

        mCardCont.setAdapter(adapter);
    }
}