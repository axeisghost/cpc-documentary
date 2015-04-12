package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenter;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class UserFriendListState extends DPState {
    private UserFriendListPresenter presenter;
    private String email;
    private Set<String> friendList;

    public UserFriendListState(String email, UserFriendListPresenter presenter) {
        this.email = email;
        this.presenter = presenter;
    }
    public Set<String> getFriendList() {
        cd(point2Dot(email));
        cd("friend");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map dummy = (HashMap)snapshot.getValue();
                if (dummy == null) {

                } else {
                    friendList = dummy.keySet();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });
        return friendList;
    }
    public void deleteFriend(String friendEmail) {
        resetFBRef2Home();
        curFBRef.child(email).child(point2Dot(friendEmail)).removeValue();
    }

}
