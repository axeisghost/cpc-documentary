package centralcpccommittee.shopwithfriends.Test;

import android.test.ActivityInstrumentationTestCase2;

import junit.framework.Assert;

import centralcpccommittee.shopwithfriends.AddSaleActivity;
import centralcpccommittee.shopwithfriends.AddSaleOnMapActivity;
import centralcpccommittee.shopwithfriends.UserProfile;
import centralcpccommittee.shopwithfriends.WelcomeActivity;

/**
 * Created by lihen_000 on 3/30/2015.
 */
public class TestAddSaleOnMap extends ActivityInstrumentationTestCase2<AddSaleActivity> {
    private UserProfile user;
    private double p1,p2;
    private double lat1,lat2,lnt1,lnt2;
    private String name1,name2;

    public TestAddSaleOnMap(){
        super(AddSaleActivity.class);
    }
    protected void setUp() throws Exception {
        super.setUp();
        user = new UserProfile("@user");
        name1 = "Apple";
        name2 = "Banana";
        p1 = 1.0;
        p2 = 2.0;
        lat1 = 2.33333;
        lnt1 = 2.33333;
        lat2 = 23.3333;
        lnt2 = 23.3333;
    }
    public void testFirstSale() {
        Assert.assertEquals(1,user.addMapItem(name1, p2, lat1, lnt1));
        Assert.assertEquals(1,user.addSaleOnMap(name1,p1,lat1,lnt1));
        Assert.assertEquals(1,user.getItemList().size());
        try {
            Assert.assertEquals(p1, user.getItemList().get(name1).get(0));
            Assert.assertEquals(lat1, user.getItemList().get(name1).get(1));
            Assert.assertEquals(lnt1, user.getItemList().get(name1).get(2));
        } catch (Exception e) {

        };
    }
    public void testNonExistItem() {
        Assert.assertEquals(0,user.addSaleOnMap(name2, p1,lat1,lnt1));
    }
}
