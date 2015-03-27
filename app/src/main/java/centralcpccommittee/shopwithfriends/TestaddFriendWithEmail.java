package centralcpccommittee.shopwithfriends;


import junit.framework.Assert;
import junit.framework.TestCase;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.SingleLaunchActivityTestCase;

/**
 * Created by Axeisghost on 3/26/2015.
 */
public class TestaddFriendWithEmail extends ActivityInstrumentationTestCase2<WelcomeActivity> {
    private UserProfile testuser;
    private String NON_EXIST_EMAIL;
    private String FirstAddEMAIL;
    private String UnmatchedUserName;
    private String FirstAddUSERNAME;
    private Activity testA;
    public TestaddFriendWithEmail() {
        super(WelcomeActivity.class);
    }
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        testA = getActivity();
        setActivityInitialTouchMode(true);
        testA = getActivity();
        testuser = new UserProfile("@cpc");
        NON_EXIST_EMAIL = "abc@gmail.com";
        FirstAddEMAIL = "@test";
        UnmatchedUserName = "wjx";
        FirstAddUSERNAME = "tester";
    }

    @Override
    public void runTest() {
    }
    public void testNonExistEmail() {
        Assert.assertEquals(0, testuser.addFriend(NON_EXIST_EMAIL, "foo"));
    }
    public void testUnmatchedUser() {
        Assert.assertEquals(1, testuser.addFriend(FirstAddEMAIL, UnmatchedUserName));
    }

    public void testSuccessfullyAddFriend() {
        Assert.assertEquals(3, testuser.addFriend(FirstAddEMAIL, FirstAddUSERNAME));
        Assert.assertEquals(true, testuser.checkFriend(FirstAddEMAIL));
    }

    public void testAlreadyFriend() {
        Assert.assertEquals(2, testuser.addFriend(FirstAddEMAIL, FirstAddUSERNAME));
    }
}
