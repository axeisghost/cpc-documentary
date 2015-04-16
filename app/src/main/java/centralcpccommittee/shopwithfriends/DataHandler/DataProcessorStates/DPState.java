package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;
import com.firebase.client.Firebase;

/**
 * Created by Yuhui on 4/7/2015.
 */
public abstract class DPState {
    protected static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";
    protected Firebase curFBRef;

    public DPState() {
        curFBRef = new Firebase(FIREBASE_URL);
    }

    public Firebase cd(String url) {
        curFBRef = curFBRef.child(url);
        return curFBRef;
    }

    public Firebase getCurFBRef() {
        return curFBRef;
    }

    public Firebase resetFBRef2Home() {
        curFBRef = new Firebase(FIREBASE_URL);
        return curFBRef;
    }
    public boolean process() {
        return false;
    }
    public static String point2Dot(String email) {
        return email.replaceAll("\\.", "<dot>");
    }
    public static String dot2PPoint(String email) {
        return email.replaceAll("<dot>", ".");
    }
    public boolean FBKeyCheck(String key) {
        return (key.contains(".") || key.contains("$") || key.contains("#") || key.contains("[") || key.contains("]") || key.contains("/"));
    }
}
