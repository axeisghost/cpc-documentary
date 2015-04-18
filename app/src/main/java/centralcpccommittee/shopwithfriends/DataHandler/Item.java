package centralcpccommittee.shopwithfriends.DataHandler;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class Item {
    private String userEmail;
    private String itemName;
    private double price;
    private double latitude;
    private double longitude;

    public Item(String userName, String itemName, double price, double latitude, double longitude) {
        this.userEmail = userName;
        this.itemName = itemName;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserName() {
        return userEmail;
    }

    public void setUserName(String userName) {
        this.userEmail = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
