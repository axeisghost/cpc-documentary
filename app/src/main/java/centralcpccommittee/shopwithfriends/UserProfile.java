package centralcpccommittee.shopwithfriends;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by Kappa on 15/02/08.
 */
public class UserProfile {
    private static final dataExchanger database = dataExchanger.getInstance();
    private final JSONObject userinfo;
    private JSONObject friends;
    private JSONObject items;
    private JSONObject sales;
    private final String userEmail;
    private String userName;

    /**
     * take in the user's email and load the profile from database
     * @param email the email of the user we want to inspect
     */
    public UserProfile(String email) {
        userEmail = email;
        userinfo = database.retrieveProfile(email);
        try {
            friends = userinfo.getJSONObject("friendlist");
            items = userinfo.getJSONObject("itemlist");
            sales = userinfo.getJSONObject("salelist");
            userName = userinfo.getString("name");
        } catch (JSONException e) {
            Log.d("JOSNException", "Unexpected non-existed profile");
        }
    }

    /**
     * return the user's friends list as a Arraylist of UserProfile
     * @return arraylist of UserProfile instances
     */

    public ArrayList<UserProfile> getFriendList() {
        ArrayList<UserProfile> re = new ArrayList<UserProfile>();
        Iterator<String> ite = friends.keys();
        while (ite.hasNext()) {
            re.add(new UserProfile(ite.next()));
        }
        return re;
    }

    public Map<String, JSONArray> getItemList() {
        Map<String, JSONArray> map = new HashMap<String, JSONArray>();
        Iterator<String> ite = items.keys();
        try {
            while (ite.hasNext()) {
                String i = ite.next();
                map.put(i, items.getJSONArray(i));
            }
        }  catch(JSONException e) {
            Log.d("JOSNException", e.getMessage());
        }
        return map;
    }
    public Map<String, JSONArray> getSaleList() {
        Map<String, JSONArray> map = new HashMap<String, JSONArray>();
        Iterator<String> ite = sales.keys();
        try {
            while (ite.hasNext()) {
                String i = ite.next();
                map.put(i, sales.getJSONArray(i));
            }
        }  catch(JSONException e) {
            Log.d("JOSNException", e.getMessage());
        }
        return map;
    }
    /**
     * take in the email of user's friend and check whether the friend is mutual or not.
     * DO NOT take in the email that is not in the friend list.
     * @param email the email of user's friend
     * @return true if mutual friends
     *          false if not.
     */

    public boolean checkFriend(String email) {
        try {
            return friends.getBoolean(email);
        } catch(JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
        return false;
    }

    /**
     * take in the email and username pair that user request to add as a friend,
     * return an integer to indicate the different outcome.
     * @param email the email that will be added as a friend
     * @param username the user of the user that will be added as friend
     * @return int 0, the email requested does not exist in database;
     *          int 1, the email and username requested does not match;
     *          int 2, the email requested is already in the friend list;
     *          int 3, the email requested is added as friend, if the opposite
     *          has added user, they both will change to mutual friends.
     */

    public int addFriend(String email, String username) {
        if (!database.retrieveEmail(email)) {
            return 0;
        } else if (!database.matchEmailName(email, username)) {
            return 1;
        } else if (!database.addFriendwithEmail(userEmail, email)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * take in the name and price of an item
     * return an integer to indicate the different outcome.
     * @param name the name of the item
     * @param price the price requirement of the item
     * @return int 0, the item already exists but price is updated
     *          int 1, the item is a new item and has been added
     */

//    public int addItem(String name, double price) {
//        if (!database.addItemWithName(userEmail, name, price)) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }

    public int addMapItem(String name, double price, double latitude,double longtitude) {
        if (!database.addMapItemWithName(userEmail, name, price, latitude, longtitude)) {
            return 0;
        } else {
            return 1;
        }
    }
    public int addSaleOnMap(String name, double price, double latitude,double longtitude) {
        if (!database.addANewSaleOnMap(userEmail, name, price, latitude, longtitude) ) {
            return 0;
        } else {
            return 1;
        }
    }


    public void deleteFriend(String FriendEmail) {
        database.rmFriend(FriendEmail, userEmail);
    }

    /**
     * return the average rate of user as double
     * @return double, the average rate of user
     */

    public double getRate() {
        try {
            double re = userinfo.getJSONObject("rate").getDouble("sum");
            if (re == Double.MIN_VALUE) {
                return -1;
            } else {
                return re/(userinfo.getJSONObject("rate").length() - 1);
            }
        } catch(JSONException e) {
            Log.d("JOSNException", e.getMessage());
        }
        return -1;
    }

    /**
     * take in the rater's email and his rate, update the user's profile
     * with the input.
     * @param raterEmail the email of the rater
     * @param rate the rate rater input
     */

    public void rateByOther(String raterEmail, double rate) {
        database.rateUser(userEmail, raterEmail, rate);
    }

    /**
     * return user's email as a string.
     * @return email of the User profile
     */

    public String getUserEmail() {
        return userEmail;
    }

    /**
     * get username
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the string type of the user information
     */
    public String toString() {
        return userEmail + " " + userName;
    }
}
