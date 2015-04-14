package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenter;

/**
 * Created by Yuhui on 4/14/2015.
 */
public class DeleteFriendState extends DPState{
    private String email;
    private String friendEmail;
    private UserFriendListPresenter presenter;
    public DeleteFriendState (String email, String friendEmail, UserFriendListPresenter presenter) {
        this.email = email;
        this.friendEmail = friendEmail;
        this.presenter = presenter;
    }

    public boolean process() {
        return true;
    }
}
