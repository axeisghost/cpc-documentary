package centralcpccommittee.shopwithfriends.Friends;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.FriendsContentState;
import centralcpccommittee.shopwithfriends.UserProfile;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
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

    private static DataProcessor dataProcessor = new DataProcessor();


    public static void addItem(FriendItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void update(Map<String, Object> friendMap) {
        clear();
        if (friendMap != null) {
            Set<String> friendList = friendMap.keySet();
            for (String thisUser: friendList) {
                if (thisUser != null) {
                    dataProcessor.setState(new FriendsContentState(thisUser));
                    dataProcessor.process();
                }
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
        public final String id;
        public final String content;

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
