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
                Log.d("IOException", "Unexcepted");
            }
        }
        try {
            while ((curr = fis.read()) != -1) {
                text.append((char)curr);
            }
            if (text.toString() == null) {

                Log.d(text.toString(), "text == null");
            } else {
                Log.d("fuckup", "shit");
            }
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

    public void registerAddition(String email, String field, String content) {
        try {
            data.getJSONObject(email).put(field, content);
            record();
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
    }

    public boolean retrieveEmail(String email) {
        return data.has(email);
    }

    public boolean checkPassword(String email, String password) {
        try {
            return data.getJSONObject(email).getString("Password").equals(password);
        } catch(JSONException e) {
            Log.d("JSONException", e.getMessage());
        }
        return false;
    }

    public void readerClose() {
        try {
            fis.close();
        } catch(IOException e) {
            Log.d("IOException", e.getMessage());
        }
    }

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
