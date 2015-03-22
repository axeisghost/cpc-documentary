package centralcpccommittee.shopwithfriends;

import android.content.Context;
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
    private static dataExchanger database = dataExchanger.getInstance();
    private JSONObject userinfo;
    private JSONObject friends;
    private JSONObject items;
    private String userEmail;
    private String userName;

    /**
     * take in the user's email and load the profile from database
     * @param email
     */
    public UserProfile(String email) {
        userEmail = email;
        userinfo = database.retrieveProfile(email);
        try {
            friends = userinfo.getJSONObject("friendlist");
            items = userinfo.getJSONObject("itemlist");
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

    public Map<String, Double> getItemList() {
        Map<String, Double> map = new HashMap<String, Double>();
        Iterator<String> ite = items.keys();
        try {
            while (ite.hasNext()) {
                String i = ite.next();
                map.put(i, items.getDouble(i));
            }
        }  catch(JSONException e) {
            Log.d("JOSNException", e.getMessage());
        }
        return map;
    }

    /**
     * take in the email of user's friend and check whether the friend is mutual or not.
     * DO NOT take in the email that is not in the friend list.
     * @param email
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
     * @param email
     * @param username
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
     * @param name
     * @param price
     * @return int 0, the item already exists but price is updated
     *          int 1, the item is a new item and has been added
     */

    public int addItem(String name, double price) {
        if (!database.addItemWithName(userEmail, name, price)) {
            return 0;
        } else {
            return 1;
        }
    }

    public int addMapItem(String name, double price, double longtitude,double latitude) {
        if (!database.addMapItemWithName(userEmail, name, price, longtitude, latitude)) {
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
     * @param raterEmail
     * @param rate
     */

    public void rateByOther(String raterEmail, double rate) {
        database.rateUser(userEmail, raterEmail, rate);
    }

    /**
     * return user's email as a string.
     * @return
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
