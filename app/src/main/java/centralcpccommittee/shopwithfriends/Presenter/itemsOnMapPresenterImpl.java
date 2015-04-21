package centralcpccommittee.shopwithfriends.Presenter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.ItemsOnMapState;
import centralcpccommittee.shopwithfriends.ItemsOnMapView;

/**
 * Created by Yuhui on 4/19/2015.
 */
public class ItemsOnMapPresenterImpl implements ItemsOnMapPresenter{
    private String userEmail;
    private ItemsOnMapView view;
    private DataProcessor dataProcessor;

    public ItemsOnMapPresenterImpl(String userEmail, ItemsOnMapView view) {
        this.userEmail = userEmail;
        this.view = view;
        dataProcessor = new DataProcessor();
        dataProcessor.setState(new ItemsOnMapState(userEmail, this));
    }

    @Override
    public void setUpMapIfNeeded() {
        view.setUpMapIfNeeded();
    }

    public void setUpMap() {
        view.setUpMap();
    }

    public void setMyLocationEnabled() {
        view.setMyLocationEnabled();
    }

    public void focusLocation() {
        dataProcessor.process();
    }

    public void noItem(LatLng curLocation) {
        view.noItem(curLocation);
    }
    public void addMarker(LatLng curLocation,String name) {
        view.addMarker(curLocation, name);
    }

    public void itemOnMap(LatLng curLocation) {
        view.itemOnMap(curLocation);
    }
}
