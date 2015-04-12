package centralcpccommittee.shopwithfriends.Presenter;

import java.util.Set;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.UserFriendListState;
import centralcpccommittee.shopwithfriends.UserFriendListView;

/**
 * Created by Yuhui on 4/11/2015.
 */
public class UserFriendListPresenterImpl implements UserFriendListPresenter {
    private String email;
    private UserFriendListView view;
    private DataProcessor dataProcessor;

    public UserFriendListPresenterImpl(String email, UserFriendListView view) {
        this.email  = email;
        this.view = view;
    }

    @Override
    public Set<String> getFriendList() {
        return ((UserFriendListState)dataProcessor.getState()).getFriendList();
    }

    @Override
    public void deleteFriend(String friendEmail) {
        ((UserFriendListState)dataProcessor.getState()).deleteFriend(friendEmail);
    }
}
