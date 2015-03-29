package centralcpccommittee.shopwithfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;


/**
 * An activity representing a single UserFriend detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link UserFriendListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link UserFriendDetailFragment}.
 */
public class UserFriendDetailActivity extends ActionBarActivity {

    private String userEmail;
    private UserProfile thisUser;
    private String friendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        thisUser = new UserProfile(userEmail);
        friendEmail = extras.getString(UserFriendDetailFragment.ARG_ITEM_ID);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfriend_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(UserFriendDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(UserFriendDetailFragment.ARG_ITEM_ID));
            UserFriendDetailFragment fragment = new UserFriendDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.userfriend_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent move = new Intent(this, UserFriendListActivity.class);
            move.putExtra("userEmail", userEmail);
            navigateUpTo(move);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteFriendPressed(@SuppressWarnings("UnusedParameters") View view) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage(getString(R.string.Hint_delete_friend)).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteFriend();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    void deleteFriend() {
        thisUser.deleteFriend(friendEmail);
        Intent move = new Intent(this, UserFriendListActivity.class);
        move.putExtra("userEmail", userEmail);
        navigateUpTo(move);
    }
}
