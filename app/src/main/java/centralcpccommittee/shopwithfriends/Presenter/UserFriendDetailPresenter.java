package centralcpccommittee.shopwithfriends.Presenter;

import java.util.Map;

/**
 * Created by Yuhui on 4/14/2015.
 */
public interface UserFriendDetailPresenter {
    public void deleteFriend(String friendEmail);
    public void updateFriendList(Map<String, Object> friendList);
}
