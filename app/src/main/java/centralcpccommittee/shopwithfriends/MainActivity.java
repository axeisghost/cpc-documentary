package centralcpccommittee.shopwithfriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.app.Fragment;


public class MainActivity extends ActionBarActivity {
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("userEmail");
            TextView view = (TextView) findViewById(R.id.main_userEmail);
            view.setText("Welcome!\nUser: " + userEmail);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void logOut() {
        Intent move = new Intent(this,WelcomeActivity.class);
        startActivity(move);
        finish();
    }

    public void testPressed(View view) {
        testUser();
    }

    public void test2Pressed(View view) {
        test2User();
    }

    private void test2User() {
        UserProfile mainuser = new UserProfile(userEmail);
        mainuser.addFriend("@cpc", "cpc");
        Log.d("mutual friend1", ""+mainuser.checkFriend("@cpc"));
        Log.d("mutual friend2", "" + new UserProfile("@cpc").checkFriend(userEmail));
    }

    private void testUser() {
        UserProfile mainuser = new UserProfile(userEmail);
        Log.d("friendaddcondition1",""+ mainuser.addFriend("@xxx", "fuck"));
        Log.d("friendaddcondition2",""+ mainuser.addFriend("@test", "fuck"));
        mainuser.addFriend("@test", "tester");
        Log.d("friendaddcondition4",""+ mainuser.addFriend("@test", "tester"));
        mainuser.addFriend("@wjx", "wjx");
        mainuser.rateByOther("@test", 4.5);
        mainuser.rateByOther("@wjx", 3.1);
        for (int ii = 0; ii < mainuser.getFriendList().size(); ii++) {
            Log.d("listOutput" + ii, mainuser.getFriendList().get(ii).getUserEmail());
        }
        Log.d("listOutput", "" + mainuser.getRate());
    }

    public void logoutPressed(View view) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage(getString(R.string.Hint_logout_confirm)).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                logOut();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    /**
     * turn to the friend list page
     */
    public void friendPage(View view) {
        Intent move = new Intent(this, UserFriendListActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
    }

    /**
     * turn to the add friend page
     * @param view
     */
    public void addFriendPage(View view) {
        Intent move = new Intent(this, AddFriendActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
    }

    /**
     * turn to the add item page
     * @param view
     */
    public void addItemPage(View view) {
        Intent move = new Intent(this, AddItemActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
    }

    public void itemListPage(View view) {
        Intent move = new Intent(this, ItemListActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
    }
}
