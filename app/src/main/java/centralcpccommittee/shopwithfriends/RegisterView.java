package centralcpccommittee.shopwithfriends;

/**
 * Created by Yuhui on 4/11/2015.
 */
public interface RegisterView {
    public void initializeError();
    public void invalidPassword();
    public void emailRequired();
    public void invalidEmail();
    public void loginCanceled();
    public void userNameRequired();
    public void unmatchedPassword();
    public void exitTheAct();
}
