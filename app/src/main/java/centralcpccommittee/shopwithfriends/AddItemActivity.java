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
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import centralcpccommittee.shopwithfriends.Presenter.AddItemPresenter;
import centralcpccommittee.shopwithfriends.Presenter.AddItemPresenterImpl;


public class AddItemActivity extends ActionBarActivity implements AddItemView{

    private EditText itemNameView, itemPriceView;
    private String mEmail;
    // --Commented out by Inspection (3/29/2015 12:52 AM):private LatLng location;
    private static double longtitude = -84.397326;
    private static double latitude = 33.777361;
    private Bundle extras;


    private AddItemPresenter presenter;
    /**
     * default onCreate method for activity, take in the account passed
     * from last activity
     * @param savedInstanceState Info from last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        extras = getIntent().getExtras();
        mEmail = extras.getString("userEmail");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        itemNameView = (EditText) findViewById(R.id.add_item_name);
        itemPriceView = (EditText) findViewById(R.id.add_item_price);
        try {
            String name = extras.getString("itemName");
            String iPrice = extras.getString("price");
            itemNameView.setText(name);
            itemPriceView.setText(iPrice);
        } catch (Exception e) {
            return true;
        }
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

    public static void updateLatLng(LatLng loc){
        longtitude = loc.longitude;
        latitude = loc.latitude;
    }
    public void addItemPressed(@SuppressWarnings("UnusedParameters") View view) {
        presenter = new AddItemPresenterImpl(mEmail, itemNameView.getText().toString(), Double.parseDouble(itemPriceView.getText().toString()), latitude,longtitude, this);
        presenter.addItem();
    }

    @Override
    public void initializeError() {
        itemNameView.setError(null);
        itemPriceView.setError(null);
    }

    public void addNotExistItem(String itemName) {
        backToMain(itemName + "has been added");
    }

    public void updateExistItem(String itemName, double price) {
        backToMain(itemName + " already exists and the price has been changed to " + price);
    }
    public void mapButtonPressed(@SuppressWarnings("UnusedParameters") View view) {
        Intent move = new Intent(this, MapsActivity.class);
        move.putExtra("itemName", itemNameView.getText().toString());
        move.putExtra("price", itemPriceView.getText().toString());
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }
}
