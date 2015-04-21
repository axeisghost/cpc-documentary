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

    public Item(String userEmail, String itemName, double price, double latitude, double longitude) {
        this.userEmail = userEmail;
        this.itemName = itemName;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userName) {
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
