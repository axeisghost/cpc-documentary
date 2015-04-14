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

    public UserFriendListState(String email, UserFriendListPresenter presenter) {
        this.email = email;
        this.presenter = presenter;
    }
    @Override
    public boolean process () {

        cd(point2Dot(email));
        cd("friend");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> friendList = (HashMap<String, Object>)snapshot.getValue();
                presenter.updateFriendList(friendList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });
        return true;
    }
    public void deleteFriend(String friendEmail) {
        resetFBRef2Home();
        curFBRef.child(email).child(point2Dot(friendEmail)).removeValue();
    }

}
