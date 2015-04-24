package centralcpccommittee.shopwithfriends.Presenter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.NotificationState;
import centralcpccommittee.shopwithfriends.SaleListActivity;
import centralcpccommittee.shopwithfriends.SaleListView;

/**
 * Created by Axeisghost on 4/23/2015.
 */
public class NotificationPresenterImpl implements  NotificationPresenter {
    private String mEmail;
    private DataProcessor dataProcessor;
    private NotificationManager NM;
    private Activity mainact;
    public NotificationPresenterImpl(String email, Activity mainact) {
        this.mEmail = email;
        dataProcessor = new DataProcessor(new NotificationState(this, mEmail));
        this.mainact = mainact;
    }
    public void salesUpdateNotify(String itemname) {
        String title = "NewSale";
        String subject = "You got a New sale";
        String body = "A new sale of " + itemname + "!";
        PendingIntent pending=PendingIntent.getActivity(
                mainact.getApplicationContext(),0, new Intent(),0);
        Notification notify=new Notification(android.R.drawable.stat_notify_more,title,System.currentTimeMillis());
        notify.setLatestEventInfo(mainact, subject, body,pending);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        NM = (NotificationManager)mainact.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.d("comeon", "Why not trigger");
        NM.notify(0, notify);
    }

    public boolean logoutProcess() {
        dataProcessor.process();
        return true;
    }
}
