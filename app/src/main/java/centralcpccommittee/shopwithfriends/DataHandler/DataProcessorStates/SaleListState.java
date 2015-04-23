package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import centralcpccommittee.shopwithfriends.Presenter.SaleListPresenter;

/**
 * Created by Yuhui on 4/22/2015.
 */
public class SaleListState extends DPState {
    private String email;
    private SaleListPresenter presenter;

    public SaleListState(String email, SaleListPresenter presenter) {
        this.email = email;
        this.presenter = presenter;
    }

    public boolean process() {
        cd(point2Dot(email));
        cd("sales");
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Map>SaleList = (HashMap<String, Map>)snapshot.getValue();
                String[] items;
                if (SaleList != null) {
                    int size = SaleList.size();
                    items = new String[size];
                    int i = 0;
                    for (Map.Entry<String, Map> element: SaleList.entrySet()) {
                        Map<String, Map> itemWrapper = element.getValue();
                        Object[] dummy = itemWrapper.keySet().toArray();
                        Map itemDetail = itemWrapper.get(dummy[0].toString());
                        items[i] = itemDetail.get("itemName").toString();
                        items[i] = items[i] + " : ";
                        double price = (double)itemDetail.get("price");
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
