package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.ItemListPresenter;
import centralcpccommittee.shopwithfriends.R;

/**
 * Created by Yuhui on 4/18/2015.
 */
public class ItemListState extends DPState {
    private String email;
    private ItemListPresenter presenter;

    public ItemListState(String email, ItemListPresenter presenter) {
        this.email = email;
        this.presenter = presenter;
    }

    public boolean process() {
        cd(point2Dot(email));
        cd("items");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Map>itemList = (HashMap<String, Map>)snapshot.getValue();
                String[] items;
                if (itemList != null) {
                    int size = itemList.size();
                    items = new String[size];
                    int i = 0;
                    for (Map.Entry<String, Map> element: itemList.entrySet()) {
                        items[i] = element.getKey().toString();
                        items[i] = items[i] + " : ";
                        double price = (double)element.getValue().get("price");
                        items[i] = items[i] + price;
                        i++;
                    }
                } else {
                    items = new String[1];
                    items[0] = "";
                }
                presenter.displayList(items);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return true;
    }
}
