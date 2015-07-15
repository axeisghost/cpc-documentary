package centralcpccommittee.shopwithfriends.Presenter;

import com.google.android.gms.maps.model.LatLng;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.SalesOnMapState;
import centralcpccommittee.shopwithfriends.ItemsOnMapView;
import centralcpccommittee.shopwithfriends.SalesOnMapView;

/**
 * Created by Yuhui on 4/23/2015.
 */
public class SalesOnMapPresenterImpl implements SalesOnMapPresenter {
    private String userEmail;
    private SalesOnMapView view;
    private DataProcessor dataProcessor;

    public SalesOnMapPresenterImpl(String userEmail, SalesOnMapView view) {
        this.userEmail = userEmail;
        this.view = view;
        dataProcessor = new DataProcessor();
        dataProcessor.setState(new SalesOnMapState(userEmail, this));
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
