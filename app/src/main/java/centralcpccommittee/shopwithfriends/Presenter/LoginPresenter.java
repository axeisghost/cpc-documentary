package centralcpccommittee.shopwithfriends.Presenter;

/**
 * Created by Yuhui on 4/8/2015.
 */
public interface LoginPresenter {
    public boolean loginUser();
    public void login();
    public void emailNotExist();
    public void loginSuccessfully();
    public void incorrectPassword();
}
