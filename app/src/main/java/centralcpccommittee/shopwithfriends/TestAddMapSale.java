package centralcpccommittee.shopwithfriends;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.json.JSONException;

/**
 * Created by Jianshi on 3/28/2015.
 */
public class TestAddMapSale extends ActivityInstrumentationTestCase2<WelcomeActivity> {
    private UserProfile testUser;
    private UserProfile friendUser;
    private String testUserName;
    private String friendUserName;
    private String itemName;
    private String item2Name;
    private String wrongItem;
    private Activity testA;

    public TestAddMapSale() {
        super(WelcomeActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        testA = getActivity();
        setActivityInitialTouchMode(true);
        testA = getActivity();
        testUser = new UserProfile("@testA");
        friendUser = new UserProfile("@testB");
        testUserName = "testA";
        friendUserName = "testB";
        itemName = "Apple";
        item2Name = "Banana";
        wrongItem = "Orange";
        testUser.addMapItem(itemName, 10.0, 10, 10);
        testUser.addMapItem(item2Name, 10.0, 10, 10);
        testUser.addFriend("@testB", friendUserName);

    }

    @Override
    public void runTest() {
    }
    public void testSet() {
        Assert.assertTrue(testUser.getSaleList().isEmpty());
    }
    public void testNotFriend() {
        Assert.assertEquals(0, friendUser.addSaleOnMap(itemName, 1.0, 11, 11));
        Assert.assertTrue(testUser.getSaleList().isEmpty());
    }
    public void testUnmatchedItem() {
        friendUser.addFriend("@testA", testUserName);
        Assert.assertEquals(0, friendUser.addSaleOnMap(wrongItem, 1.0, 11, 11));
        Assert.assertTrue(testUser.getSaleList().isEmpty());
    }
    public void testHigherPrice() {
        friendUser.addFriend("@testA", testUserName);
        Assert.assertEquals(0, friendUser.addSaleOnMap(itemName, 11.0, 11, 11));
        Assert.assertTrue(testUser.getSaleList().isEmpty());
    }
    public void testMatchItem() {
        friendUser.addFriend("@testA", testUserName);
        Assert.assertEquals(1, friendUser.addSaleOnMap(itemName, 1.0, 11, 11));
        Assert.assertEquals(1, testUser.getSaleList().size());
        try {
            Assert.assertEquals(1.0, testUser.getSaleList().get(itemName).get(0));
            Assert.assertEquals(11, testUser.getSaleList().get(itemName).get(1));
            Assert.assertEquals(11, testUser.getSaleList().get(itemName).get(2));
        } catch (JSONException e) {
        };
        Assert.assertEquals(1, friendUser.addSaleOnMap(itemName, 2.0, 12, 12));
        Assert.assertEquals(1, testUser.getSaleList().size());
        try {
            Assert.assertEquals(2.0, testUser.getSaleList().get(itemName).get(0));
            Assert.assertEquals(12, testUser.getSaleList().get(itemName).get(1));
            Assert.assertEquals(12, testUser.getSaleList().get(itemName).get(2));
        } catch (JSONException e) {
        };
        Assert.assertEquals(1, friendUser.addSaleOnMap(item2Name, 2.0, 12, 12));
        Assert.assertEquals(2, testUser.getSaleList().size());
        try {
            Assert.assertEquals(2.0, testUser.getSaleList().get(item2Name).get(0));
            Assert.assertEquals(12, testUser.getSaleList().get(item2Name).get(1));
            Assert.assertEquals(12, testUser.getSaleList().get(item2Name).get(2));
        } catch (JSONException e) {
        };
    }
}
