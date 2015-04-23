package centralcpccommittee.shopwithfriends.Presenter;

import android.app.Activity;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import centralcpccommittee.shopwithfriends.DataHandler.DataProcessor;
import centralcpccommittee.shopwithfriends.DataHandler.DataProcessorStates.NotificationState;

/**
 * Created by Axeisghost on 4/23/2015.
 */
public class NotificationPresenterImpl implements  NotificationPresenter {
    private String mEmail;
    private DataProcessor dataProcessor;
    private NotificationManager NM;
    private Activity mainact;
    public NotificationPresenterImpl(String email, Activity mainact) {
        dataProcessor = new DataProcessor(new NotificationState(this, mEmail));
        this.mainact = mainact;
    }
    public void salesUpdateNotify() {
        String title = "New sale from your friend!";
        String subject = "Sale";
        String body = "You got a new sale from your friend";
        NM = (NotificationManager)mainact.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification(android.R.drawable.
                stat_notify_more,title,System.currentTimeMillis());
        PendingIntent pending=PendingIntent.getActivity(
                mainact.getApplicationContext(),0, new Intent(),0);
        notify.setLatestEventInfo(mainact.getApplicationContext(),subject,body,pending);
        NM.notify(0, notify);
    }
}
