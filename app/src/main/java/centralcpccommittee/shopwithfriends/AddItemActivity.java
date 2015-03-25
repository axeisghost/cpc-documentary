package centralcpccommittee.shopwithfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class AddItemActivity extends ActionBarActivity {

    private EditText itemNameView, itemPriceView;
    private String mEmail;
    private UserProfile user;
    private LatLng location;
    private static double longtitude;
    private static double latitude;
    /**
     * default onCreate method for activity, take in the account passed
     * from last activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Bundle extras = getIntent().getExtras();
        mEmail = extras.getString("userEmail");

        user = new UserProfile(mEmail);
        Log.d("bug", mEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        itemNameView = (EditText) findViewById(R.id.add_item_name);
        itemPriceView = (EditText) findViewById(R.id.add_item_price);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * attempt to add an item on map
     */
    public void attemptAddItem() {
        itemNameView.setError(null);
        itemPriceView.setError(null);
        String itemName = itemNameView.getText().toString();
        double itemPrice = Double.parseDouble(itemPriceView.getText().toString());
      //  int type = user.addItem(itemName, itemPrice);
        Bundle extras = getIntent().getExtras();
      /*  if(extras.getString("longtitude")!=null && extras.getString("latitude")!=null  ) {
            longtitude = Double.parseDouble(extras.getString("longtitude"));
            latitude = Double.parseDouble(extras.getString("latitude"));
        } else {
            longtitude = 38;
            latitude = 38;
        }*/
        int type = user.addMapItem(itemName, itemPrice, latitude,longtitude);
        String message = "";
        if (type == 0) {
            message = itemName + " already exists and the price has been changed to " + itemPrice  + " " + latitude + " " + longtitude;
        } else {
            message = itemName + "has been added";
        }
        backToMain(message);
    }
    public void mapButtonPressed(View view) {
        Intent move = new Intent(this, MapsActivity.class);
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }
    /**
     * return method after added friend
     * @param message
     */




    private void backToMain(String message) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage(message).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exitTheAct();
                            }
                        });
        builder.create().show();
    }

    /**
     * exit the activity
     */
    public void exitTheAct() {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }

    /**
     * check the validity if the email typed in
     * @param email
     * @return true if the email is valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static void updateLatLng(LatLng loc){
        longtitude = loc.longitude;
        latitude = loc.latitude;
    }
    public void addItemPressed(View view) {
        attemptAddItem();
    }
}
