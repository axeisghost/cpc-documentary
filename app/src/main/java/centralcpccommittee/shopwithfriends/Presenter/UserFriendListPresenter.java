package centralcpccommittee.shopwithfriends.Presenter;

import java.util.Set;

/**
 * Created by Yuhui on 4/11/2015.
 */
public interface UserFriendListPresenter {
    public Set<String> getFriendList();
    public void deleteFriend(String friendEmail);
}
