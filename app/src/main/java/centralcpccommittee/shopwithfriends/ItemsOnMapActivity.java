package centralcpccommittee.shopwithfriends;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.Map;

public class ItemsOnMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String userEmail;
    private UserProfile user;
    private Map<String, JSONArray> itemMap;
    private LatLng focusLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_on_map);

        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        user = new UserProfile(userEmail);
        itemMap = user.getItemList();



        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        try {
            focusLocation();
        } catch (Exception e) {
            System.out.println("Fail to locate");
        }

    }
    private void focusLocation(){
        LatLng curLocation;
        String name;
        Double curLat = 33.777361,curLont = -84.397326;
        Double maxLat=-999.0,minLat=999.0,maxLont=-999.0,minLont=999.0;
        curLocation = new LatLng(curLat,curLont);
        if (!itemMap.isEmpty()) {
            int size = itemMap.size();
            for (Map.Entry<String,JSONArray> element: itemMap.entrySet()) {
                name = element.getKey().toString();
                name = name + " : ";
                JSONArray jData = element.getValue();
                try {
                    name = name + jData.getDouble(0);
                    name = name + " : ";
                    curLont = jData.getDouble(1);
                    name = name + curLont;
                    curLat = jData.getDouble(2);
                    name = name + " : ";
                    name = name + curLat;
                } catch (Exception e) {
                    System.out.println("JSON must be kidding !!!");
                }
                curLocation = new LatLng(curLat,curLont);
                mMap.addMarker(new MarkerOptions().position(curLocation).title(name));
                if(curLat>maxLat) maxLat = curLat;
                if(curLat<minLat) minLat = curLat;
                if(curLont > maxLont) maxLont = curLont;
                if(curLont < minLont) minLont = curLont;
            }
            LatLngBounds curBound = new LatLngBounds(new LatLng(minLat,minLont), new LatLng(maxLat,maxLont));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curBound.getCenter(), 17));
        } else {
            mMap.addMarker(new MarkerOptions().position(curLocation).title("Duang!!!!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(curLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        }
    }
    public void backToItemListActivityPressed(View view) {
        Intent move = new Intent(this, ItemListActivity.class);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        move.putExtra("userEmail",userEmail);
        startActivity(move);
    }
}
