package centralcpccommittee.shopwithfriends;

import java.util.Map;

/**
 * Created by Yuhui on 3/21/2015.
 */
public class Item {
    private String name;
    private Double price;
    private String location;
    private Map info;

    public Item() {

    }
    public Item(String name, Double price, String location, Map info) {
        this.name = name;
        this.price = price;
        this.location = location;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map getInfo() {
        return info;
    }

    public void setInfo(Map info) {
        this.info = info;
    }
}
