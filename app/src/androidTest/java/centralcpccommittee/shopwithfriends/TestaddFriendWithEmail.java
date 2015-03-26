package centralcpccommittee.shopwithfriends;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
/**
 * Created by Axeisghost on 3/26/2015.
 */
public class TestaddFriendWithEmail {
    private UserProfile testuser;
    private String SelfEmail = "@cpc";
    private String NON_EXIST_EMAIL = "abc@gmail.com";
    private String FirstAddEMAIL = "@test";
    private String UnmatchedUserName = "wjx";
    private String FirstAddUSERNAME = "tester";
    public void setupUserProfile() {
        testuser = new UserProfile(SelfEmail);
    }
    @Test
    private void testNonExistEmail() {
        setupUserProfile();
        assertEquals(testuser.addFriend(NON_EXIST_EMAIL, "foo"), 0);
    }
    @Test
    private void testUnmatchedUser() {
        setupUserProfile();
        assertEquals(testuser.addFriend(FirstAddEMAIL, UnmatchedUserName), 1);
    }
    @Test
    private void SuccessfullyAddFriend() {
        setupUserProfile();
        assertEquals(testuser.addFriend(FirstAddEMAIL, FirstAddUSERNAME), 3);
        assertEquals(testuser.checkFriend(FirstAddEMAIL), true);
    }
    @Test
    private void testAlreadyFriend() {
        setupUserProfile();
        assertEquals(testuser.addFriend(FirstAddEMAIL, FirstAddUSERNAME), 2);
    }


}
