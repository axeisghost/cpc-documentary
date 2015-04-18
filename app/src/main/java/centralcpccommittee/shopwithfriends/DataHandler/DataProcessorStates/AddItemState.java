package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
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
        itemBuffer = new Item(email, itemName, price, latitude, longitude);
        cd(point2Dot(email));
        cd("items");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map itemList = (HashMap)snapshot.getValue();
                if (itemList == null) {
                    addItem();
                    presenter.addNotExistItem(itemName);
                } else if (itemList.get(itemName) == null) {
                    addItem();
                    presenter.addNotExistItem(itemName);
                } else {
                    addItem();
                    presenter.updateExistItem(itemName, price);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            private void addItem() {
                curFBRef.child(itemName).setValue(itemBuffer);
            }
        });
    return true;
    }
}
