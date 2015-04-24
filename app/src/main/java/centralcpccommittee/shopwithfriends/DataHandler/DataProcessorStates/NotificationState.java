package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

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
    public NotificationState(NotificationPresenter inpresenter, String inmEmail) {
        this.mEmail = inmEmail;
        this.presenter = inpresenter;
        this.firstcheck = false;
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
                    firstcheck = true;
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
        curFBRef.addChildEventListener(myListener);
    }
    public boolean process() {
        curFBRef.removeEventListener(myListener);
        return true;
    }
}
