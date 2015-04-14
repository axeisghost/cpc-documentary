package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Friends.FriendsContent;
import centralcpccommittee.shopwithfriends.Presenter.AddFriendPresenter;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class AddFriendState extends DPState {
    private String email;
    private String friendEmail;
    private String friendName;
    private AddFriendPresenter presenter;
    public static final int NOT_MUTUAL = 0;
    public static final int MUTUAL = 0;

    public AddFriendState(String email, String friendEmail, String friendName, AddFriendPresenter presenter) {
        this.email = email;
        this.friendEmail = friendEmail;
        this.friendName = friendName;
        this.presenter = presenter;
    }

    public boolean process() {
        if (email.equals(friendEmail)) {
            presenter.addYourself();
            return false;
        }
        cd(point2Dot(friendEmail));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map dummy = (HashMap)snapshot.getValue();
                if (dummy == null) {
                    presenter.emailNotRegistered();
                } else {
                    if (! dummy.get("name").equals(friendName)) {
                        presenter.emailUserNameNoMatch();
                    } else {
                        resetFBRef2Home();
                        cd(point2Dot(email));
                        cd("friend");
                        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                String message;
                                Map<String, Object> dummy = (HashMap<String,Object>)(snapshot.getValue());
                                if (dummy == null) {
                                    curFBRef.child(point2Dot(friendEmail)).setValue(1);
                                    message = friendName +
                                            " is successfully added as your friend!";
                                } else if (dummy.get(point2Dot(friendEmail)) ==  null) {
                                    curFBRef.child(point2Dot(friendEmail)).setValue(NOT_MUTUAL);
                                    message = friendName +
                                            " is successfully added as your friend!";
                                } else {
                                    message = friendName + " is already your friend!";
                                }
                                presenter.backToMain(message);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Log.d("firebase Error: ", firebaseError.getMessage());
                            }
                        });
                        curFBRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                FriendsContent.update((HashMap<String,Object>)(snapshot.getValue()));
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                                Log.d("firebase Error: ", firebaseError.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });

        return true;
    }
}
