package centralcpccommittee.shopwithfriends.Presenter;

import android.text.TextUtils;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.RegisterState;
import centralcpccommittee.shopwithfriends.RegisterView;

/**
 * Created by Yuhui on 4/10/2015.
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    private String password;
    private String confirmPassword;
    private String userName;
    private String email;
    private DataProcessor dataProcessor;
    private RegisterView view;

    public RegisterPresenterImpl(String password, String confirmPassword, String userName,
                                 String email, RegisterView view) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.userName = userName;
        this.view = view;
        dataProcessor = new DataProcessor();
    }

    public void attemptRegister() {
        boolean proceed = false;
        view.initializeError();
        if (TextUtils.isEmpty(email)) {
            view.emailRequired();
        } else if (! isEmailValid(email)) {
            view.invalidEmail();
        } else if (TextUtils.isEmpty(userName)) {
            view.userNameRequired();
        } else  if (! TextUtils.isEmpty(password) && ! isPasswordValid(password)) {
            view.invalidPassword();
        } else if (!(confirmPassword.equals(password))) {
            view.unmatchedPassword();
        } else {
            proceed = true;
        }
        if (! proceed) {
            view.loginCanceled();
        } else {
            register();
        }
    }

    private void register() {
        dataProcessor.setState(new RegisterState(this, email, userName, password));
        dataProcessor.process();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public void backToWelcome() {
        view.backToWelcome();
    }

    @Override
    public void existedEmail() {
        view.existedEmail();
    }

    @Override
    public void initializeError() {
        view.initializeError();
    }
}
