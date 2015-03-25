package centralcpccommittee.shopwithfriends;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jianshi on 3/9/2015.
 */
public class saleType {
    private double price;
    private double latitude,longtitude;

    public saleType(double price, double latitude,double longtitude){
        this.price = price;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public double getPrice() {
        return price;
    }

    public LatLng getLoc(){
        return new LatLng(latitude,longtitude);
    }

}
