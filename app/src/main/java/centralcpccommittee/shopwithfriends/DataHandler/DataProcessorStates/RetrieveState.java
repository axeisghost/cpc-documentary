package centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import centralcpccommittee.shopwithfriends.Presenter.LoginPresenter;

/**
 * Created by Axeisghost on 4/23/2015.
 */
public class RetrieveState extends DPState{
    private LoginPresenter presenter;
    private String mEmail;
    private Activity myActivity;
    public RetrieveState(String userEmail, Activity putin, LoginPresenter mypre) {
        this.mEmail = userEmail;
        this.myActivity = putin;
        this.presenter = mypre;
    }

    public boolean process() {
        cd(point2Dot(mEmail));
        curFBRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map dummy = (HashMap)snapshot.getValue();
                if (dummy == null) {
                    presenter.emailNotExist();
                } else {
                    Intent i = new Intent("com.google.android.gm.action.AUTO_SEND");
                    i.setType("plain/text");
                    String[] reciver = new String[] {mEmail };
                    String subject = "Password of account in Shop with Friends";
                    String body = "Your passowrd is " + dummy.get("password");
                    i.putExtra(android.content.Intent.EXTRA_EMAIL, reciver);
                    i.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(android.content.Intent.EXTRA_TEXT, body);
                    //myActivity.startActivity(i);
                    presenter.sentEmail();
                }
            }
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("firebase Error: ", firebaseError.getMessage());
            }
        });
        return false;
    }
}
