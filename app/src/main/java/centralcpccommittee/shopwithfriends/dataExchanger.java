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
    private JSONObject data;
    private int curr;
    private StringBuilder text = new StringBuilder();
    private Context fileContext;
    private String name;

    /**
     * Constructor of dataExchanger class, take in the fileName of
     * primarty database which store the users' information.
     * The primary database was converted to JSON Object The context
     * is the context of application which is used to utilize internal
     * storage.
     * @param fileName
     * @param appContext
     */

    public dataExchanger(String fileName, Context appContext) {
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
//            if (text.toString() == null) {
//
//                Log.d(text.toString(), "text == null");
//            } else {
//                Log.d("fuckup", "shit");
//            }
            if (text.toString().isEmpty()) {
                data = new JSONObject();
            } else {
                data = new JSONObject(text.toString());
            }
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

    public boolean registerUser(String email, String password) {
        try {
            if (data.has(email)) {
                return false;
            }
            data.put(email, new JSONObject().put("Password", password));
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

    public void registerAddition(String email, String field, String content) {
        try {
            data.getJSONObject(email).put(field, content);
            record();
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
            fos = fileContext.openFileOutput(name, fileContext.MODE_PRIVATE);
            fos.write(data.toString().getBytes());
            //Log.d("fuck", data.toString());
            fos.close();
            readerClose();
        } catch(IOException e) {
            Log.d("IOException", e.getMessage());
        }
    }
}
