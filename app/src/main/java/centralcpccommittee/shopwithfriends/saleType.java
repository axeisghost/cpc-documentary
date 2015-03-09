package centralcpccommittee.shopwithfriends;

/**
 * Created by Jianshi on 3/9/2015.
 */
public class saleType {
    private double price;
    private String location;

    public saleType(double price, String location){
        this.price = price;
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public String getLoc(){
        return location;
    }

}
