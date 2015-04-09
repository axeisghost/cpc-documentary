package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.LoginView;
import centralcpccommittee.shopwithfriends.Presenter.LoginPresenter;

/**
 * Created by Yuhui on 4/8/2015.
 */
public class LoginState extends DPState {
    private LoginPresenter presenter;
    private String mEmail;
    private String mPassword;

    public LoginState(LoginPresenter presenter, String mEmail, String mPassword) {
        super();
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.presenter = presenter;
    }

    @Override
    public boolean process() {
        cd(point2Dot(mEmail));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map dummy = (HashMap)snapshot.getValue();
                if (snapshot.getValue() == null) {
                    presenter.emailNotExist();
                } else if (mPassword.equals(dummy.get("password"))) {
                    presenter.loginSuccessfully();
                } else {
                    presenter.incorrectPassword();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });
        return false;
    }
}
