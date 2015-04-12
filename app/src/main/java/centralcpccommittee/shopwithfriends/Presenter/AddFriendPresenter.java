package centralcpccommittee.shopwithfriends.Presenter;

/**
 * Created by Yuhui on 4/11/2015.
 */
public interface AddFriendPresenter {
    public void attemptFindAndAdd();
    public void emailNotRegistered();
    public void emailUserNameNoMatch();
    public void addYourself();
    public void backToMain(String message);
}
