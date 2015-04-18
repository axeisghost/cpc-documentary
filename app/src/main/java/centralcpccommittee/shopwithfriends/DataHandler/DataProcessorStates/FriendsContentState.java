package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Friends.FriendsContent;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class FriendsContentState extends DPState {
    private String thisUser;
    public FriendsContentState(String thisUser) {
        this.thisUser = thisUser;
    }
    @Override
    public boolean process() {
        cd(point2Dot(thisUser));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {

            private String renewInfo(Map<String, Object> userProfile, String info) {
                Map<String, Map> items = (Map<String, Map>) userProfile.get("items");
                if (items != null) {
                    for (Map.Entry<String, Map> element: items.entrySet()) {
                        String output = element.getKey().toString() + " : " + element.getValue().get("price").toString();
                        info = info + output + "\n";
                    }
                }
                return info;
            }

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String info = null;
                Map<String, Object> userProfile = (HashMap<String, Object>)snapshot.getValue();
                String name = (String)(userProfile.get("name"));
                if ((double)userProfile.get("rating") == -1.0) {
                    info = "Email: " + dot2PPoint(thisUser) + "\n"
                            + "Username: " + name + "\n"
                            + "User's Rating: " + "Not Applicable" + "\n"
                            + "Posted items:" + "\n";
                   info = renewInfo(userProfile, info);
                } else {
                    info =  "Email: " + dot2PPoint(thisUser) + "\n"
                            +"Username: " + name + "\n"
                            + "User's Rating: " + userProfile.get("Rating") + "\n"
                            + "Posted items:" + "\n";
                    info = renewInfo(userProfile, info);
                }
                FriendsContent.addItem(new FriendsContent.FriendItem(name, info));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });
        return true;
    }
}
