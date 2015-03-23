package centralcpccommittee.shopwithfriends;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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

    private String userEmail;
    private String userName;

    private static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";
    private Firebase mFirebaseRef = new Firebase(FIREBASE_URL);
    private Firebase userRef;

    private Map info;
    private Map friends;
    private Map items;
    private Map sales;
    private Map allUserList;
    private Map rates;


    /**
     * take in the user's email and load the profile from database
     * @param email
     */
    public UserProfile(String email) {
        userEmail = email;
        userEmail = RegisterActivity.replaceDot(userEmail);
        this.mFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                allUserList =(Map) snapshot.getValue();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        userRef = mFirebaseRef.child(userEmail);
        this.userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object o = snapshot.getValue();
                // is the user email doesn't exist
                if (o == null) {
                   Log.d("Unexpected Erro", "User doesn't exist which doesn't make sense at all!");
                } else {
                    info = (Map) (((Map) o).get("info"));
                    friends = (Map) (((Map) o).get("friends"));
                    items = (Map) (((Map) o).get("items"));
                    sales = (Map) (((Map) o).get("sales"));
                    rates = (Map)(((Map) o).get("rates"));
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }



    /**
     * return the user's friends list as a Arraylist of UserProfile
     * @return arraylist of UserProfile instances
     */

    public Map<String, Long> getFriendList() {
        return friends;
    }

    public Map<String, Map> getItemList() {
        return items;
    }

    public Map<String, Map> getSaleList() {
        return sales;
    }
    public Map<String, Map> getRateList() {
        return rates;
    }

    /**
     * take in the email of user's friend and check whether the friend is mutual or not.
     * DO NOT take in the email that is not in the friend list.
     * @param email
     * @return true if mutual friends
     *          false if not.
     */
    // TODO: move this method to where it is used
    public boolean checkFriend(String email) {
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
    // TODO: move this method to where it is used
    //TODO: fix addFriend
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
    // TODO: move this method to where it is used
    public int addItem(String name, double price) {
        if (!database.addItemWithName(userEmail, name, price)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * take in the name, price, and the location of a sale
     * return an integer to indicate the different outcome.
     * @param name
     * @param price
     * @return int 0, no request can be satisfied with this sale
     *          int 1, the sale is successfully reported and at least one friend gets the message
     */
    // TODO: move this method to where it is used
    // TODO: Fix this!

    public int addSale(String name, double price, String loc) {
        /*
        if (!database.addANewSale(userEmail, name, price, loc)) {
            return 0;
        } else {
            return 1;
        }
        */
        return 0;
    }

    public void deleteFriend(String FriendEmail) {
        database.rmFriend(FriendEmail, userEmail);
    }
    // TODO: move this method to where it is used
    // TODO: Fix deleteFriend
    /**
     * return the average rate of user as double
     * @return double, the average rate of user
     */

    public double getRate() {
        // TODO: move this method to where it is used
        /*
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
        */
        return -1;
    }

    /**
     * take in the rater's email and his rate, update the user's profile
     * with the input.
     * @param raterEmail
     * @param rate
     */

    public void rateByOther(String raterEmail, double rate) {
        // TODO: Fix this
        // TODO: move this method to where it is used
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

    public Firebase getUserRef() {
        return userRef;
    }


}
