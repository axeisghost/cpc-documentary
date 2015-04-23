package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.DataHandler.Item;
import centralcpccommittee.shopwithfriends.Presenter.AddItemPresenter;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class AddItemState extends DPState {
    private AddItemPresenter presenter;
    private String email;
    private String itemName;
    private double price;
    private double latitude;
    private double longitude;
    private Item itemBuffer;

    public AddItemState(AddItemPresenter presenter, String email, String itemName, double price, double latitude, double longitude) {
        this.presenter = presenter;
        this.email = email;
        this.itemName = itemName;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean process() {
        itemBuffer = new Item(email,itemName, price, latitude, longitude);
        //cd(point2Dot(email));
        cd("itemList");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map itemList = (HashMap<String, HashMap>)snapshot.getValue();
                if (itemList == null) {
                    addItem();
                    presenter.addNotExistItem(itemName);
                } else {
                    try {
                        ((HashMap<String, HashMap>)itemList.get(itemName)).get("wantedUsers").get(point2Dot(email));
                        addItem();
                        presenter.updateExistItem(itemName, price);
                    } catch (Exception e) {
                        addItem();
                        presenter.addNotExistItem(itemName);
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }

            private void addItem() {
                Firebase link = curFBRef.child(itemName).child("wantedUsers").child(point2Dot(email));
                link.setValue(itemBuffer);
                resetFBRef2Home();
                curFBRef.child(point2Dot(email)).child("items").child(itemName).setValue(itemBuffer);
            }
        });
    return true;
    }
}
