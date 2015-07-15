package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.UserFriendDetailPresenter;
import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenter;

/**
 * Created by Yuhui on 4/14/2015.
 */
public class DetailDeleteFriendState extends DPState {
    private String email;
    private String friendName;
    private UserFriendDetailPresenter presenter;
    private  String friendEmail;
    public DetailDeleteFriendState (String email, String friendName, UserFriendDetailPresenter presenter) {
        this.email = email;
        this.friendName = friendName;
        this.presenter = presenter;
    }

    public boolean process() {
        curFBRef.child("userList").child(point2Dot(friendName)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                friendEmail = (String) snapshot.getValue();
                curFBRef.child(point2Dot(email)).child("friend").child(point2Dot(friendEmail)).removeValue();

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
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });

        return true;
    }

}
