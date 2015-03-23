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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Map;


public class AddItemActivity extends ActionBarActivity {

    private EditText itemNameView, itemPriceView;
    private String mEmail;
    private UserProfile user;
    private Item itemBuffer;

    private static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";
    private Firebase mFirebaseRef = new Firebase(FIREBASE_URL);
    private Firebase userRef;

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
        userRef = user.getUserRef();
        Log.d("bug", mEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        itemNameView = (EditText) findViewById(R.id.add_item_name);
        itemPriceView = (EditText) findViewById(R.id.add_sale_name);
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
    public void attemptAddItem() {
        itemNameView.setError(null);
        itemPriceView.setError(null);
        String itemName = itemNameView.getText().toString();
        double itemPrice = Double.parseDouble(itemPriceView.getText().toString());
        itemBuffer = new Item(itemName, itemPrice, null, null);
        userRef.child("items").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String message = AddItemActivity.this.itemBuffer.getName() +
                        "  has been added";
                Map o = (Map)(snapshot.getValue());
                if (o == null) {
                    //Do Nothing
                } else if (o.containsKey(AddItemActivity.this.itemBuffer.getName())) {
                    message = AddItemActivity.this.itemBuffer.getName() +
                            " already exists and the price has been changed to " +
                            AddItemActivity.this.itemBuffer.getPrice();
                } else {
                    //Do Nothing
                }
                AddItemActivity.this.userRef.child("items").
                        child(AddItemActivity.this.itemBuffer.getName()).
                        setValue(AddItemActivity.this.itemBuffer);
                AddItemActivity.this.backToMain(message);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
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

    public void addItemPressed(View view) {
        attemptAddItem();
    }
}
