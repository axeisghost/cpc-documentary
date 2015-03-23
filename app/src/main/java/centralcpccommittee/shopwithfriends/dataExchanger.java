package centralcpccommittee.shopwithfriends;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.google.android.gms.games.snapshot.Snapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by axeisghost on 15/01/30.
 */
public class dataExchanger {
    private static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";

    // private ValueEventListener mConnectedListener;

    private FileOutputStream fos;
    private FileInputStream fis;
    private static JSONObject data;
    private int curr;
    private StringBuilder text = new StringBuilder();
    private Context fileContext;
    private String name;


    private static dataExchanger instance;
    private Firebase userRef;
    private Firebase mFirebaseRef;
    private String nameBuffer;
    private String passwordBuffer;

    private volatile boolean registered = false;
    private User userBuffer;

    public static void initialize(String filename, Context context) {
        instance = new dataExchanger(filename, context);
    }

    public static dataExchanger getInstance() {
        return instance;
    }

    /**
     * Constructor of dataExchanger class, take in the fileName of
     * primary database which store the users' information.
     * The primary database was converted to JSON Object The context
     * is the context of application which is used to utilize internal
     * storage.
     * @param fileName
     * @param appContext
     */

    private dataExchanger(String fileName, Context appContext) {
        //TODO: Check internet connection here
        //TODO: handle no connection here, somehow
        mFirebaseRef = new Firebase(FIREBASE_URL);
    }

    public void rmFriend(String FriendEmail, String selfEmail) {
        try {
            data.getJSONObject(selfEmail).getJSONObject("friendlist").remove(FriendEmail);
            record();
        } catch(JSONException e) {
            Log.d("Unexpected", "Friend not exist");
        }
    }

    /**
     * Take in a pair of email and password, put
     * the key and value pair into JSONObject that converted
     * from the primary database;
     * @param email
     * @param password
     * @return
     */

    public void registerUser(String email, String password, String username) {
        email = replaceDot(email);
        passwordBuffer = password;
        nameBuffer = username;
        userRef = mFirebaseRef.child(email);
        registerHelper(userRef);
    }

    private void registerHelper(Firebase userRef) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {
                    //dataExchanger.this.userBuffer = new User(dataExchanger.this.nameBuffer, dataExchanger.this.passwordBuffer, );
                    dataExchanger.this.registered = true;
                    dataExchanger.this.userRef.setValue(userBuffer);
                    Log.d("register", "new user registered");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public boolean getRegisterStatus() {
        return registered;
    }



    private String replaceDot(String email) {
        return email.replaceAll("\\.", "<dot>");
    }

    /**
     * take in users' email and the name and content of addition information,
     * add as a new key-value pair in the nested JSON obejct.
     * @param email
     * @param field
     * @param content
     */

    private void registerAddition(String email, String field, Object content) {
        try {
            email = replaceDot(email);
            //Test if the dir exist. Expect to have exception here to register user
            Firebase userRef = mFirebaseRef.child(email);
            (new Firebase(userRef + "/" + field)).setValue(content);
        } catch (Exception e) {
            Log.d("FirebaseException","email doesn't exist");
        }

        try {
            data.getJSONObject(email).put(field, content);
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
    }

    /**
     * take in the email and find whether the email exists in the database
     * or not.
     * @param email
     * @return true or false
     */
    public boolean retrieveEmail(String email) {
        try {
            email = replaceDot(email);
            //Test if the dir exist. Expect to have exception here to register user
            mFirebaseRef.child(email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean matchEmailName(String email, String username) {
        try {
            mFirebaseRef.child(email);
            //TODO: retrieve data
            return true;
        } catch(Exception e) {
            Log.d("fireBase exception","email doesn't exist");
            return false;
        }
        /*try {
            return username.equals(data.getJSONObject(email).getString("name"));
        } catch (JSONException e) {
            Log.d("JSONException", "Unexcepted JSON Exception. name should exist");
        }
        return false;*/
    }

    public boolean addFriendwithEmail(String selfEmail, String email) {
        try {
            JSONObject selfList = data.getJSONObject(selfEmail).getJSONObject("friendlist");
            JSONObject otherList = data.getJSONObject(email).getJSONObject("friendlist");
            if (selfList.has(email)) {
                return false;
            } else if (otherList.has(selfEmail)){
                otherList.put(selfEmail, true);
                selfList.put(email, true);
                record();
                return true;
            } else {
                selfList.put(email, false);
                record();
                return true;
            }
        } catch(JSONException e) {
            Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
        }
        return false;
    }


    public boolean addItemWithName(String selfEmail, String name, double price) {
        try {
            JSONObject selfList = data.getJSONObject(selfEmail).getJSONObject("itemlist");
            if (selfList.has(name)) {
                selfList.put(name, price);
                record();
                return false;
            }
            selfList.put(name, price);
            record();
            return true;
        } catch(JSONException e) {
            Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
        }
        return false;
    }
/*
    public boolean addANewSale(String selfEmail, String name, double price, String loc) {
        boolean flag = false;
        try {
            UserProfile user = new UserProfile(selfEmail);
            ArrayList<UserProfile> list = user.getFriendList();
            for (UserProfile u: list) {
                if (u != null) {
                    Map<String, Double> items = u.getItemList();
                    if (items.containsKey(name) && price <= items.get(name)) {
                        JSONObject sList = data.getJSONObject(u.getUserEmail()).getJSONObject("salelist");
                        JSONArray arr = new JSONArray();
                        arr.put(0, price);
                        arr.put(1, loc);

                        // saleType s = new saleType(price, loc);
                        // sList.put(name, s);

                        sList.put(name, arr);
                        record();
                        flag = true;
                    }
                }
            }
        } catch(JSONException e) {
            Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
        }
        return flag;
    }
*/

    public void rateUser(String selfEmail, String raterEmail, double rate) {
        try {
            JSONObject userRate = data.getJSONObject(selfEmail).getJSONObject("rate");
            double sum = userRate.getDouble("sum");
            if (userRate.has(raterEmail)) {
                double temp = userRate.getDouble(raterEmail);
                userRate.put("sum", sum + rate - temp);
            } else if (userRate.get("sum").equals(Double.MIN_VALUE)) {
                userRate.put("sum", rate);
            } else {
                userRate.put("sum", sum + rate);
            }
            userRate.put(raterEmail, rate);
            record();
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
    }
    /**
     * take in a pair of email and password and check the JSONObject converted
     * from the database to whether the email correspond to the password.
     * @param email
     * @param password
     * @return
     */

    public boolean checkPassword(String email, String password) {
        try {
            return data.getJSONObject(email).getString("Password").equals(password);
        } catch(JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
        return false;
    }

    public JSONObject retrieveProfile(String email) {
        try {
            return data.getJSONObject(email);
        } catch(JSONException e) {
            Log.d("JSONException", "Unexpected non-existed email");
        }
        return null;
    }

    /**
     * close the io class of primary database but will be used only the database
     * was read but not written.
     */

    public void readerClose() {
        try {
            fis.close();
        } catch(IOException e) {
            Log.d("IOException", e.getMessage());
        }
    }

    /**
     * record the change of primary database and close the io class. Should be used
     * only the database was edited.
     */

    private void record() {
        try {
            fos = fileContext.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(data.toString().getBytes());
            fos.close();
            //readerClose();
        } catch(IOException e) {
            Log.d("IOException", e.getMessage());
        }
    }
}
