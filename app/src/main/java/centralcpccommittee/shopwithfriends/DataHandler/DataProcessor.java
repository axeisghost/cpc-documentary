package centralcpccommittee.shopwithfriends.DataHandler;

import android.app.Activity;

import com.firebase.client.Firebase;

/**
 * Created by Yuhui on 4/7/2015.
 */
public class DataProcessor {
    private static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";
    private Firebase curFBRef;
    private Activity curActivity;

    public DataProcessor(Activity curActivity) {
        curFBRef = new Firebase(FIREBASE_URL);
        this.curActivity = curActivity;
    }

    public Firebase cd(String url) {
        curFBRef = curFBRef.child(url);
        return curFBRef;
    }

    public Firebase resetCurFBRef() {
        curFBRef = new Firebase(FIREBASE_URL);
        return curFBRef;
    }

    public Firebase getmFirebaseRef() {
        return curFBRef;
    }
}
