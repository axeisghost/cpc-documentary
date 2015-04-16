package centralcpccommittee.shopwithfriends.Presenter;

import android.text.TextUtils;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.LoginState;
import centralcpccommittee.shopwithfriends.LoginView;

/**
 * Created by Yuhui on 4/8/2015.
 */
public class LoginPresenterImpl implements LoginPresenter{
    private String mEmail;
    private String mPassword;
    private LoginView loginView;
    private DataProcessor dataProcessor;

    public LoginPresenterImpl(String mEmail, String mPassword, LoginView loginView) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.loginView = loginView;
        dataProcessor = new DataProcessor();
    }

    public boolean loginUser() {
        boolean proceed = false;
        loginView.initializeError();
        if (TextUtils.isEmpty(mEmail)) {
            loginView.emailRequired();
        } else if (! isEmailValid(mEmail)) {
            loginView.invalidEmail();
        } else if (!TextUtils.isEmpty(mPassword) && !isPasswordValid(mPassword)) {
            loginView.invalidPassword();
        } else {
            proceed = true;
        }
        if (! proceed) {
            loginView.loginCanceled();
        } else {
            dataProcessor.setState(new LoginState(this, mEmail, mPassword));
            loginView.proceedToLogin();
        }
        return proceed;
    }

    public void login() {
        dataProcessor.process();
    }

    @Override
    public void emailNotExist() {
        loginView.emailNotExist();
    }

    @Override
    public void loginSuccessfully() {
        loginView.loginSuccessfully();
    }

    @Override
    public void incorrectPassword() {
        loginView.incorrectPassword();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}
