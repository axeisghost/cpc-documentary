package centralcpccommittee.shopwithfriends.Friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import centralcpccommittee.shopwithfriends.Friend;

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
    public static List<Friend> ITEMS = new ArrayList<Friend>();

    /**
     * A map of friend items, by ID.
     */
    public static Map<String, Friend> ITEM_MAP = new HashMap<String, Friend>();

    static {
        // Add 3 sample items.
    }

    public static void addItem(Friend item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void clear() {
        ITEM_MAP = new HashMap<String, Friend>();
        ITEMS = new ArrayList<Friend>();
    }





}
