package com.example.tartanhacks.meetup;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("UserActivity")
public class UserActivity extends ParseObject {
    public String getName() {
        return getString("name");
    }

    public void setName(String value) {
        put("name", value);
    }

    public ParseRelation<ParseUser> getUsersLiked(){
        return getRelation("usersLiked");
    }

    public void addUsersLiked(ParseUser user){
        getUsersLiked().add(user);
    }

    public ParseRelation<ParseUser> getUsersDisliked(){
        return getRelation("usersLiked");
    }

    public void addUsersDisliked(ParseUser user){
        getUsersDisliked().add(user);
    }

    public String getCategory() {
        return getString("category");
    }

    public void setCategory(String value) {
        put("category", value);
    }

    public int getNumOfPeople() {
        return getInt("numOfPeople");
    }

    public void setNumOfPeople(int numOfPeople) {
        put("numOfPeople", numOfPeople);
    }

    public static ParseQuery<UserActivity> getQuery() {
        return ParseQuery.getQuery(UserActivity.class);
    }
}