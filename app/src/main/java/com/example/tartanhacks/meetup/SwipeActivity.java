package com.example.tartanhacks.meetup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
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
                        ParseRelation<ParseUser> relation = a.getUsersSeen();
                        ParseQuery seenQuery = relation.getQuery();
                        seenQuery.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                        userSeen = false;
                        try {
                            int n = seenQuery.find().size();
                            if (seenQuery.find().size() > 0) {
                                System.out.println("userSeen is true " + n);
                                userSeen = true;
                            }
                        } catch (ParseException E) {
                        }
                        if (!userSeen)
                            adapter.add(newCard(a));
                        mCardCont.setAdapter(adapter);
                        a.saveInBackground();
                    }
                }
            }
        });
    }

    public void showCards() {
        updateCards();
        //need refresh button
        mCardCont.setAdapter(adapter);
        if (currCards.size() == 0) {
            findViewById(R.id.cardCont).setVisibility(View.GONE);
            findViewById(R.id.noMoreCards).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cardCont).setVisibility(View.VISIBLE);
            findViewById(R.id.noMoreCards).setVisibility(View.GONE);
        }

    }

    public CardModel newCard(final UserActivity a){
        String activityName = a.getName();
        final String activityID = a.getObjectId();
        currCards.add(activityID);
        CardModel card = new CardModel(activityName, "Description goes here",
                ContextCompat.getDrawable(getApplicationContext(), R.drawable.picture1));
        card.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                System.out.println("Swipeable Cards" + " DISPLAY MORE INFO");
            }
        });

        card.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() { // onDISLIKE, swiping LEFT
                a.addUsersSeen(ParseUser.getCurrentUser());
                a.saveInBackground();
                currCards.remove(activityID);
                adapter.pop();
                updateCards();
            }

            @Override
            public void onDislike() { // onLIKE, swiping RIGHT
                ParseUser u = ParseUser.getCurrentUser();
                a.addUsersSeen(u);
                a.saveInBackground();
                u.getRelation("likedActivities").add(a);
                currCards.remove(activityID);
                adapter.pop();
                updateCards();
            }
        });
        return card;
    }
}