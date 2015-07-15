package centralcpccommittee.shopwithfriends.Presenter;

import java.util.Map;
import java.util.Set;

/**
 * Created by Yuhui on 4/11/2015.
 */
public interface UserFriendListPresenter {
    public void processFriendList();
    public void deleteFriend(String friendEmail);
    public void updateFriendList(Map<String, Object> friendList);
}
