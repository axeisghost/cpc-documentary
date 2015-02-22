package centralcpccommittee.shopwithfriends;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by axeisghost on 15/01/30.
 */
public class dataExchanger {
    private FileOutputStream fos;
    private FileInputStream fis;
    private static JSONObject data;
    private int curr;
    private StringBuilder text = new StringBuilder();
    private Context fileContext;
    private String name;
    private static dataExchanger instance;

    public static void initialize(String filename, Context context) {
        instance = new dataExchanger(filename, context);
    }

    public static dataExchanger getInstance() {
        return instance;
    }

    /**
     * Constructor of dataExchanger class, take in the fileName of
     * primarty database which store the users' information.
     * The primary database was converted to JSON Object The context
     * is the context of application which is used to utilize internal
     * storage.
     * @param fileName
     * @param appContext
     */

    private dataExchanger(String fileName, Context appContext) {
        this.fileContext = appContext;
        this.name = fileName;
        try {
            fis = fileContext.openFileInput(name);
        } catch (IOException e) {
            try {
                fos = fileContext.openFileOutput(name, Context.MODE_PRIVATE);
                fos.close();
                fis = fileContext.openFileInput(name);
            } catch (IOException err) {
                //Log.d("IOException", "Unexcepted");
            }
        }
        try {
            while ((curr = fis.read()) != -1) {
                text.append((char)curr);
            }
            if (text.toString().isEmpty()) {
                data = new JSONObject();
            } else {
                data = new JSONObject(text.toString());
            }
            Log.d("gan", ""+data.has("@qwe"));
        } catch(IOException e) {
            Log.d("IOException", e.getMessage());
        } catch(JSONException e) {
            Log.d("JSONException", e.getMessage());
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

    public boolean registerUser(String email, String password, String username) {
        try {
            if (data.has(email)) {
                return false;
            }
            data.put(email, new JSONObject().put("Password", password));
            registerAddition(email, "name", username);
            registerAddition(email, "rate", new JSONObject().put("sum", Double.MIN_VALUE));
            registerAddition(email, "friendlist", new JSONObject());
            //TODO: Posted sales structure;
            record();
        } catch(JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
        return true;
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
        return data.has(email);
    }

    public boolean matchEmailName(String email, String username) {
        try {
            return username.equals(data.getJSONObject(email).getString("name"));
        } catch (JSONException e) {
            Log.d("JSONException", "Unexcepted JSON Exception. name should be existed");
        }
        return false;
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
