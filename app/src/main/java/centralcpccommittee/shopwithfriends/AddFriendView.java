package centralcpccommittee.shopwithfriends;

/**
 * Created by Yuhui on 4/11/2015.
 */
public interface AddFriendView {
    public void initializeError();
    public void friendEmailRequired();
    public void friendEmailInvalid();
    public void friendUserNameRequired();
    public void notProceed();
    public void emailNotRegistered();
    public void emailUserNameNoMatch();
    public void addYourself();
    public void backToMain(String message);


}
