package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.HashMap;
import java.util.Map;
import centralcpccommittee.shopwithfriends.Presenter.SalesOnMapPresenter;

/**
 * Created by Yuhui on 4/23/2015.
 */
public class SalesOnMapState extends DPState {
    private String userEmail;
    private SalesOnMapPresenter presenter;
    private LatLng curLocation;

    public SalesOnMapState(String userEmail, SalesOnMapPresenter presenter) {
        this.userEmail = userEmail;
        this.presenter = presenter;
    }

    @Override
    public boolean process() {
        Double curLat = 33.777361,curLont = -84.397326;
        curLocation = new LatLng(curLat,curLont);
        presenter.setMyLocationEnabled();
        cd(point2Dot(userEmail));
        cd("sales");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, HashMap> itemMap = (HashMap<String, HashMap>)snapshot.getValue();
                if (itemMap == null) {
                    presenter.noItem(curLocation);
                } else {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Map.Entry<String,HashMap> element: itemMap.entrySet()) {
                        Map<String, Map> itemWrapper = element.getValue();
                        Object[] dummy = itemWrapper.keySet().toArray();
                        Map itemDetail = itemWrapper.get(dummy[0].toString());
                        String name;
                        name = itemDetail.get("itemName").toString();
                        name = name + ":";
                        double price = (double)(itemDetail.get("price"));
                        name = name + price;
                        double curLat = (double)(itemDetail.get("latitude"));
                        double curLong = (double)(itemDetail.get("longitude"));
                        curLocation = new LatLng(curLat,curLong);
                        presenter.addMarker(curLocation, name);
                        builder.include(curLocation);
                    }
                    presenter.itemOnMap(curLocation);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return true;
    }

}
