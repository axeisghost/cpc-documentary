package centralcpccommittee.shopwithfriends.Presenter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DPState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DeleteFriendState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.UserFriendListState;
import centralcpccommittee.shopwithfriends.Friends.FriendsContent;
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
        dataProcessor = new DataProcessor();
    }

    @Override
    public void processFriendList() {
        dataProcessor.setState(new UserFriendListState(email, this));
        dataProcessor.process();
    }

    @Override
    public void deleteFriend(String friendName) {
       dataProcessor.setState(new DeleteFriendState(email, friendName, this));
        dataProcessor.process();
    }

    public void updateFriendList(Map<String, Object> friendList) {
        FriendsContent.update(friendList);
    }
}
