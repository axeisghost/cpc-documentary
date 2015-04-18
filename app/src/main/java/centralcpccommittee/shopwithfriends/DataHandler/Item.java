package centralcpccommittee.shopwithfriends.DataHandler;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class Item {
    private String userName;
    private String itemName;
    private String price;
    private double latitude;
    private double longitude;

    public Item(String userName, String itemName, String price, double latitude, double longitude) {
        this.userName = userName;
        this.itemName = itemName;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
