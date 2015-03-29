package centralcpccommittee.shopwithfriends;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import org.json.JSONException;

/**
 * Created by TIANCHENG on 3/29/2015.
 */
public class TestAddMapItem extends ActivityInstrumentationTestCase2<WelcomeActivity> {
    private UserProfile testUser;
    private String NEW_ITEM_NAME1, NEW_ITEM_NAME2;
    private String EXISTED_ITEM_NAME;
    private double price1, price2, updatedPrice1;
    private double latitude1, longitude1, updatedLat, updatedLong, latitude2, longitude2;

    public TestAddMapItem() {
        super(WelcomeActivity.class);
    }

    // Set up all the variables
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);
        Activity testA;
        testA = getActivity();
        setActivityInitialTouchMode(true);
        testA = getActivity();
        testUser = new UserProfile("@testUser");
        NEW_ITEM_NAME1 = "newItem1";
        EXISTED_ITEM_NAME = "newItem1";
        NEW_ITEM_NAME1 = "newItem2";
        price1 = 10.0;
        price2 = 20.0;
        updatedPrice1 = 30.0;
        latitude1 = 11.111111;
        longitude1 = -11.111111;
        latitude2 = 22.222222;
        longitude2 = -22.222222;
        updatedLat = 33.333333;
        updatedLong = -33.333333;
    }

    @Override
    public void runTest() {
    }

    // dummy test before all the tests
    public void testSet() {
        Assert.assertTrue(testUser.getItemList().isEmpty());
    }

    // add the first new item
    public void testNewItem() {
        Assert.assertEquals(1, testUser.addMapItem(NEW_ITEM_NAME1, price1, latitude1, longitude1));
        Assert.assertEquals(1, testUser.getItemList().size());
        try {
            Assert.assertEquals(price1, testUser.getItemList().get(NEW_ITEM_NAME1).get(0));
            Assert.assertEquals(latitude1, testUser.getItemList().get(NEW_ITEM_NAME1).get(1));
            Assert.assertEquals(longitude1, testUser.getItemList().get(NEW_ITEM_NAME1).get(2));
        } catch (JSONException e) {
        } ;
    }

    // add the item with the same name as the first one but with different price and position
    public void testExistedItem() {
        Assert.assertEquals(0, testUser.addMapItem(EXISTED_ITEM_NAME, updatedPrice1,
                updatedLat, updatedLong));
        Assert.assertEquals(1, testUser.getItemList().size());
        try {
            Assert.assertEquals(updatedPrice1, testUser.getItemList().get(EXISTED_ITEM_NAME).get(0));
            Assert.assertEquals(updatedLat, testUser.getItemList().get(EXISTED_ITEM_NAME).get(1));
            Assert.assertEquals(updatedLong, testUser.getItemList().get(EXISTED_ITEM_NAME).get(2));
        } catch (JSONException e) {
        } ;
    }

    // add a new item not existed
    public void testNewItem2() {
        Assert.assertEquals(1, testUser.addMapItem(NEW_ITEM_NAME2, price2, latitude2, longitude2));
        Assert.assertEquals(2, testUser.getItemList().size());
        try {
            Assert.assertEquals(price2, testUser.getItemList().get(NEW_ITEM_NAME2).get(0));
            Assert.assertEquals(latitude2, testUser.getItemList().get(NEW_ITEM_NAME2).get(1));
            Assert.assertEquals(longitude2, testUser.getItemList().get(NEW_ITEM_NAME2).get(2));
        } catch (JSONException e) {
        } ;
    }

}
