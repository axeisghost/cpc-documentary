package centralcpccommittee.shopwithfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;


public class AddSaleActivity extends ActionBarActivity {

    private EditText saleName, salePrice;// --Commented out by Inspection (3/29/2015 12:52 AM):saleLoc;
    private String mEmail;
    private UserProfile user;
    private static double latitude,longtitude;
    // --Commented out by Inspection (3/29/2015 12:59 AM):private boolean locationChosen;
    /**
     * default onCreate method for activity, take in the account passed
     * from last activity
     * @param savedInstanceState Info from last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        Bundle extras = getIntent().getExtras();
        mEmail = extras.getString("userEmail");
        user = new UserProfile(mEmail);
        Log.d("bug", mEmail);
//        locationChosen = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_sale, menu);
        saleName = (EditText) findViewById(R.id.add_item_price);
        salePrice = (EditText) findViewById(R.id.add_sale_price);
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
     * attempt to add an item
     */
    void attemptAddSale() {
        saleName.setError(null);
        salePrice.setError(null);
        String name = saleName.getText().toString();
        double price = Double.parseDouble(salePrice.getText().toString());
    //    String loc = saleLoc.getText().toString();

        int type = user.addSaleOnMap(name, price, latitude, longtitude);
        String message = "Your sale has been successfully reported";
        backToMain(message);
    }

    /**
     * return method after added friend
     * @param message message that will be shown to user
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
    void exitTheAct() {
        Intent move = new Intent(this, MainActivity.class);
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }

    /**
     * check the validity if the email typed in
     * @param email the email that will be checked
     * @return true if the email is valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    public static void updateLatLng(LatLng loc){
        longtitude = loc.longitude;
        latitude = loc.latitude;
    }

    /**
     * go to Google map to select a valid location
     * @param view view form the UI
     */
    public void reportOnMapPressed(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, AddSaleOnMapActivity.class);
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }
    public void addSalePressed(@SuppressWarnings("UnusedParameters") View view) {
        attemptAddSale();
    }
}
