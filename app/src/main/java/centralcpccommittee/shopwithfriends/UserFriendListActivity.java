package centralcpccommittee.shopwithfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import centralcpccommittee.shopwithfriends.Friends.FriendsContent;
import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenter;
import centralcpccommittee.shopwithfriends.Presenter.UserFriendListPresenterImpl;


/**
 * An activity representing a list of UserFriends. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link UserFriendDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link UserFriendListFragment} and the item details
 * (if present) is a {@link UserFriendDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link UserFriendListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class UserFriendListActivity extends ActionBarActivity
        implements UserFriendListFragment.Callbacks, UserFriendListView{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private String userEmail;
    private String friendName;
    private UserFriendListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        presenter = new UserFriendListPresenterImpl(userEmail, this);
        presenter.processFriendList();



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userfriend_list);

        if (findViewById(R.id.userfriend_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            Log.d("take a peek", "right place");

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((UserFriendListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.userfriend_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link UserFriendListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(UserFriendDetailFragment.ARG_ITEM_ID, id);
            UserFriendDetailFragment fragment = new UserFriendDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.userfriend_detail_container, fragment)
                    .commit();
            friendName = id;
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, UserFriendDetailActivity.class);
            detailIntent.putExtra(UserFriendDetailFragment.ARG_ITEM_ID, id);
            detailIntent.putExtra("userEmail", userEmail);
            startActivity(detailIntent);
        }
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
        //onCreate(new Bundle());
    }

    void deleteFriend() {
        presenter.deleteFriend(friendName);
        //user.deleteFriend(friendEmail);
        Intent move = new Intent(this, UserFriendListActivity.class);
        move.putExtra("userEmail", userEmail);
        navigateUpTo(move);
    }


}
