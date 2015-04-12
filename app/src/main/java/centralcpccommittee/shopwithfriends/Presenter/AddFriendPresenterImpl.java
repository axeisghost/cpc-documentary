package centralcpccommittee.shopwithfriends.Presenter;

import android.text.TextUtils;

import centralcpccommittee.shopwithfriends.AddFriendView;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.AddFriendState;
import centralcpccommittee.shopwithfriends.R;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class AddFriendPresenterImpl implements  AddFriendPresenter {
    private String email;
    private String friendEmail;
    private String friendUserName;
    private AddFriendView view;
    private DataProcessor dataProcessor;

    public AddFriendPresenterImpl(String email, String friendEmail, String friendUserName, AddFriendView view){
        this.email = email;
        this.friendEmail = friendEmail;
        this.friendUserName = friendUserName;
        this.view = view;
    }
    @Override
    public void attemptFindAndAdd() {
        view.initializeError();
        boolean proceed = false;
        if (TextUtils.isEmpty(friendEmail)) {
            view.friendEmailRequired();
        } else if (!isEmailValid(friendEmail)) {
            view.friendEmailInvalid();
        } else if (TextUtils.isEmpty(friendUserName)) {
            view.friendUserNameRequired();
        } else {
            proceed = true;
        }
        if (! proceed) {
            view.notProceed();
        } else {
            dataProcessor = new DataProcessor(new AddFriendState(email, friendEmail, friendUserName, this));
            dataProcessor.process();
        }

    }

    @Override
    public void emailNotRegistered() {
        view.emailNotRegistered();
    }

    @Override
    public void emailUserNameNoMatch() {
        view.emailUserNameNoMatch();
    }

    @Override
    public void addYourself() {
        view.addYourself();
    }

    @Override
    public void backToMain(String message) {
        view.backToMain(message);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
}
