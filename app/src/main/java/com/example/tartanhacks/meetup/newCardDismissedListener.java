package com.example.tartanhacks.meetup;

import com.andtinder.model.CardModel;
import com.parse.ParseUser;

/**
 * Created by jae on 2/6/16.
 */
public class newCardDismissedListener implements CardModel.OnCardDimissedListener {
    UserActivity a;
    String id;

    public newCardDismissedListener(UserActivity act) {
        this.a = act;
        this.id = this.a.getObjectId();
        System.out.println(this.a.getName() + " " + this.id);
    }

    @Override
    public void onLike() {// onDISLIKE, swiping LEFT
        System.out.println("IN ONLIKE " + this.a.getName() + " " + this.id);
        SwipeActivity.removeCurrCard();
        //SwipeActivity.updateCards();
    }

    @Override
    public void onDislike() { // onLIKE, swiping RIGHT
        System.out.println("IN ON DISLIKE " + this.a.getName() + " " + this.id);
        SwipeActivity.removeCurrCard();
        ParseUser u = ParseUser.getCurrentUser();
        u.getRelation("likedActivities").add(this.a);
        u.saveInBackground();
    }
}