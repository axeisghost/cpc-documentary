package centralcpccommittee.shopwithfriends.Friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import centralcpccommittee.shopwithfriends.UserProfile;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FriendsContent {

    /**
     * An array of friend items.
     */
    public static List<FriendItem> ITEMS = new ArrayList<FriendItem>();

    /**
     * A map of friend items, by ID.
     */
    public static Map<String, FriendItem> ITEM_MAP = new HashMap<String, FriendItem>();

    static {
        // Add 3 sample items.
    }

    private static void addItem(FriendItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    /**
     * Update the items according to the friend list
     * @param friendList
     */
    public static void update(ArrayList<UserProfile> friendList) {
        clear();
        for (UserProfile thisUser: friendList) {
            if (thisUser != null) {
                String id = thisUser.getUserEmail();
                // The info contains the username, rate and the no. of reports.
                // Need to be fixed later
                String info;
                if (thisUser.getRate() == -1) {
                    info = "Username: " + thisUser.getUserName() + "\n"
                            + "User's Rate: " + "Not Applicable" + "\n"
                            + "Reports to me: 0";
                } else {
                    info = "Username: " + thisUser.getUserName() + "\n"
                            + "User's Rate: " + thisUser.getRate() + "\n"
                            + "Reports to me: 0";
                }
                addItem(new FriendItem(id, info));
            }
        }
    }

    private static void clear() {
        ITEMS = new ArrayList<FriendItem>();
        ITEM_MAP = new HashMap<String, FriendItem>();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class FriendItem {
        public String id;
        public String content;

        public FriendItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
