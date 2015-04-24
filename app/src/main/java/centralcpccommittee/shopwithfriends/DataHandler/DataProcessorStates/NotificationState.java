package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

import centralcpccommittee.shopwithfriends.Presenter.NotificationPresenter;

/**
 * Created by Axeisghost on 4/23/2015.
 */
public class NotificationState extends DPState {
    private NotificationPresenter presenter;
    private String mEmail;
    private ChildEventListener myListener;
    private boolean firstcheck;
    private long count;
    private long length = Long.MAX_VALUE;
    public NotificationState(NotificationPresenter inpresenter, String inmEmail) {
        this.mEmail = inmEmail;
        this.presenter = inpresenter;
        this.firstcheck = false;
        this.count = 0;
        Log.d("why", mEmail);
        cd(point2Dot(mEmail));
        cd("sales");
        myListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (firstcheck) {
                    String itemname = null;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        itemname = (String)((HashMap)(child.getValue())).get("itemName");
                    }
                    presenter.salesUpdateNotify(itemname);
                } else {
                    if (count >= (length - 1)) {
                        firstcheck = true;
                        String itemname = null;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            itemname = (String)((HashMap)(child.getValue())).get("itemName");
                        }
                        presenter.salesUpdateNotify(itemname);
                    } else {
                        count++;
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                length = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        curFBRef.addChildEventListener(myListener);
    }
    public boolean process() {
        curFBRef.removeEventListener(myListener);
        return true;
    }
}
