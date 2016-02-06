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
import java.util.Stack;

public class SwipeActivity extends Activity {
    ParseQuery<UserActivity> activities_query = ParseQuery.getQuery(UserActivity.class);
    static CardContainer mCardCont;
    static SimpleCardStackAdapter adapter;
    boolean userSeen;
    static Stack<String> currCards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_swipe);
        mCardCont = (CardContainer) findViewById(R.id.cardCont);
        adapter = new SimpleCardStackAdapter(this);
        currCards = new Stack<>();
        findViewById(R.id.cardCont).setVisibility(View.VISIBLE);
        findViewById(R.id.noMoreCards).setVisibility(View.GONE);
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
                            if (n > 0) {
                                System.out.println("userSeen is true " + n + " " + a.getObjectId());
                                userSeen = true;
                            }
                        } catch (ParseException E) {
                        }
                        if (!userSeen) {
                            a.addUsersSeen(ParseUser.getCurrentUser());
                            a.saveInBackground();
                            currCards.push(a.getObjectId());
                            CardModel card = new CardModel(a.getName(), "Description goes here",
                                    ContextCompat.getDrawable(getApplicationContext(), R.drawable.picture1));
                            card.setOnClickListener(new CardModel.OnClickListener() {
                                @Override
                                public void OnClickListener() {
                                    System.out.println("Swipeable Cards" + " DISPLAY MORE INFO");
                                }
                            });
                            card.setOnCardDimissedListener(new newCardDismissedListener(a));
                            adapter.add(card);
                        }
                    }
                    mCardCont.setAdapter(adapter);
                }
            }

        });
    }

    public void showCards() {
        updateCards();
        mCardCont.setAdapter(adapter);
    }

    public void refresh(View view){
        updateCards();
        System.out.println(currCards.size());
        for (int i = 0; i < currCards.size(); i++) {
            System.out.println(currCards.get(i));

        }
        if (currCards.size() == 0) {
            findViewById(R.id.cardCont).setVisibility(View.GONE);
            findViewById(R.id.noMoreCards).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cardCont).setVisibility(View.VISIBLE);
            findViewById(R.id.noMoreCards).setVisibility(View.GONE);
        }
    }

    public static void removeCurrCard(){
        String id = currCards.pop();
        System.out.println(id);
        adapter.pop();
        mCardCont.setAdapter(adapter);
    }

}

