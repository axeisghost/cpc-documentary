package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.app.Activity;

/**
 * Created by Yuhui on 4/7/2015.
 */
public abstract class DPState {
    private Activity curActivity;

    public DPState(Activity curActivity) {
        this.curActivity = curActivity;
    }
    public String point2Dot(String email) {
        return email.replaceAll("\\.", "<dot>");
    }
    public String dot2PPoint(String email) {
        return email.replaceAll("(dot)", ".");
    }
    public boolean FBKeyCheck(String key) {
        return (key.contains(".") || key.contains("$") || key.contains("#") || key.contains("[") || key.contains("]") || key.contains("/"));
    }
}
