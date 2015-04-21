package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.provider.ContactsContract;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.ItemsOnMapPresenter;

/**
 * Created by Yuhui on 4/19/2015.
 */
public class ItemsOnMapState extends DPState {
    private String userEmail;
    private ItemsOnMapPresenter presenter;
    private LatLng curLocation;

    public ItemsOnMapState(String userEmail, ItemsOnMapPresenter presenter) {
        this.userEmail = userEmail;
        this.presenter = presenter;
    }

    @Override
    public boolean process() {
        Double curLat = 33.777361,curLont = -84.397326;
        curLocation = new LatLng(curLat,curLont);
        presenter.setMyLocationEnabled();
        cd(point2Dot(userEmail));
        cd("items");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, HashMap> itemMap = (HashMap<String, HashMap>)snapshot.getValue();
                if (itemMap == null) {
                    presenter.noItem(curLocation);
                } else {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Map.Entry<String,HashMap> element: itemMap.entrySet()) {
                        String name;
                        name = element.getKey().toString();
                        name = name + ":";
                        double price = (double)(element.getValue().get("price"));
                        name = name + price;
                        double curLat = (double)(element.getValue().get("latitude"));
                        double curLong = (double)(element.getValue().get("longitude"));
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
