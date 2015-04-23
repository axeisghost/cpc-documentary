package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import centralcpccommittee.shopwithfriends.Presenter.NotificationPresenter;

/**
 * Created by Axeisghost on 4/23/2015.
 */
public class NotificationState extends DPState {
    private NotificationPresenter presenter;
    private String mEmail;
    public NotificationState(NotificationPresenter inpresenter, String inmEmail) {
        this.mEmail = inmEmail;
        this.presenter = inpresenter;
        Log.d("why", mEmail);
        cd(point2Dot(mEmail));
        cd("sales");
        curFBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                presenter.salesUpdateNotify();
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
        });
    }
}
