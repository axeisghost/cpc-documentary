package centralcpccommittee.shopwithfriends;

/**
 * Created by Yuhui on 4/8/2015.
 */
public interface LoginView {
    public void initializeError();
    public void invalidPassword();
    public void emailRequired();
    public void invalidEmail();
    public void loginCanceled();
    public void proceedToLogin();
    public void incorrectPassword();
    public void emailNotExist();
    public void loginSuccessfully();
}
