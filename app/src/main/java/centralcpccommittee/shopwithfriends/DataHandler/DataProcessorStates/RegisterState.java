package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;
import centralcpccommittee.shopwithfriends.DataHandler.User;
import centralcpccommittee.shopwithfriends.Presenter.RegisterPresenter;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class RegisterState extends DPState {
    private RegisterPresenter presenter;
    private String email;
    private String username;
    private String password;
    public RegisterState (RegisterPresenter presenter, String email, String username, String password) {
        this.presenter = presenter;
        this.email = email;
        this.username = username;
        this.password = password;

    }
    public boolean process() {
        cd(point2Dot(email));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map dummy = (HashMap)snapshot.getValue();
                if (dummy == null) {
                    User newUser = new User(email, username, password);
                    curFBRef.setValue(newUser);
                    presenter.backToWelcome();
                } else {
                    presenter.existedEmail();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }

}
