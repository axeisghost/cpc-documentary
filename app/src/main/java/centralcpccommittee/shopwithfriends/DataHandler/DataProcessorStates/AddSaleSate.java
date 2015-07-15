package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.DataHandler.Item;
import centralcpccommittee.shopwithfriends.Presenter.AddSalePresenter;

/**
 * Created by Yuhui on 4/21/2015.
 */
public class AddSaleSate extends DPState {
    private String userEmail;
    private double price;
    private double latitude;
    private double longitude;
    private String saleName;
    private AddSalePresenter presenter;
    private Item itemBuffer;
    private String nameBuffer;

    public AddSaleSate(String userEmail, double price, double latitude, double longitude, String saleName, AddSalePresenter presenter) {
        this.userEmail = userEmail;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.saleName = saleName;
        this.presenter = presenter;
    }

    public boolean process() {
        cd("itemList");
        cd(saleName);
        //cd("saleList");
        itemBuffer = new Item(userEmail, saleName, price, latitude, longitude);
        curFBRef.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, HashMap> itemData = (Map<String, HashMap>) snapshot.getValue();
                Firebase itemUrl;
                if (itemData == null) {
                    itemUrl = curFBRef.child("saleList").child(":1");
                } else {
                    Map<String, HashMap> sales = itemData.get("saleList");
                    if (sales == null){
                        itemUrl = curFBRef.child("saleList").child(":1");
                    } else {
                        itemUrl = curFBRef.child("saleList").child(":" + (new Integer(sales.size() + 1)).toString());
                    }
                }
                itemUrl.setValue(itemBuffer);
                Map<String, HashMap> wantedUsers = null;
                try {
                    wantedUsers = (Map<String, HashMap>) itemData.get("wantedUsers");
                } catch (Exception e){
                    return;
                }

                if (wantedUsers == null) {
                    return;
                } else {
                    for (Map.Entry<String, HashMap> element: wantedUsers.entrySet()) {
                        String userEmail = element.getKey();
                        resetFBRef2Home();
                        cd(point2Dot(userEmail));
                        cd("sales");
                        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Map sales = (Map<String, HashMap>) snapshot.getValue();
                                Firebase itemUrl = curFBRef;
                                if (sales == null) {
                                    itemUrl = curFBRef.child(":1").child(saleName);
                                } else {
                                    itemUrl = curFBRef.child(":" + (new Integer(sales.size() + 1)).toString()).child(saleName);
                                }
                                itemUrl.setValue(itemBuffer);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }
}
