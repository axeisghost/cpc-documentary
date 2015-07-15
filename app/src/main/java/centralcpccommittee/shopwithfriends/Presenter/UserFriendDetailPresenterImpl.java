package centralcpccommittee.shopwithfriends.Presenter;

import java.util.Map;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DeleteFriendState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.DetailDeleteFriendState;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.UserFriendListState;
import centralcpccommittee.shopwithfriends.Friends.FriendsContent;
import centralcpccommittee.shopwithfriends.UserFriendDetailView;
import centralcpccommittee.shopwithfriends.UserFriendListView;

/**
 * Created by Yuhui on 4/14/2015.
 */
public class UserFriendDetailPresenterImpl implements UserFriendDetailPresenter {
    private String email;
    private String friendEmail;
    private UserFriendDetailView view;
    private DataProcessor dataProcessor;

    public UserFriendDetailPresenterImpl(String email, UserFriendDetailView view) {
        this.email  = email;
        this.view = view;
        dataProcessor = new DataProcessor();
    }

    @Override
    public void deleteFriend(String friendEmail) {
        dataProcessor.setState(new DetailDeleteFriendState(email, friendEmail, this));
        dataProcessor.process();
    }

    public void updateFriendList(Map<String, Object> friendList) {
        FriendsContent.update(friendList);
    }
}
