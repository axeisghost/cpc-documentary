package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import centralcpccommittee.shopwithfriends.LoginActivity;

/**
 * Created by Yuhui on 4/8/2015.
 */
public class LoginState extends DPState {
    private String mEmail;
    private String mPassword;

    public LoginState(LoginActivity loginActivity) {
        super(loginActivity);
        mEmail = loginActivity.getmEmailView().getText().toString();
        mPassword = loginActivity.getmPasswordView().getText().toString();
    }
}
