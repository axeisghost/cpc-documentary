package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;
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

    }
    public boolean process() {
        cd(point2Dot(email));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }

}
