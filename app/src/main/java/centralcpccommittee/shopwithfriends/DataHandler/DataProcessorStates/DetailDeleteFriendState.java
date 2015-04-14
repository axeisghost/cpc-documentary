package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import centralcpccommittee.shopwithfriends.Presenter.UserFriendDetailPresenter;
import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenter;

/**
 * Created by Yuhui on 4/14/2015.
 */
public class DetailDeleteFriendState extends DPState {
    private String email;
    private String friendEmail;
    private UserFriendDetailPresenter presenter;
    public DetailDeleteFriendState (String email, String friendEmail, UserFriendDetailPresenter presenter) {
        this.email = email;
        this.friendEmail = friendEmail;
        this.presenter = presenter;
    }

    public boolean process() {
        return true;
    }

}
