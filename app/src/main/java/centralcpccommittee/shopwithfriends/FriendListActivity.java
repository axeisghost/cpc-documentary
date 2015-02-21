package centralcpccommittee.shopwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by TIANCHENG on 2/17/2015.
 */
public class FriendListActivity extends Activity {

    private dataExchanger mData = dataExchanger.getInstance();
    private String userEmail;
    private UserProfile user;
    private ArrayList<UserProfile> friendList;

    /**
     * create the user friend list view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        Bundle extras = getIntent().getExtras();
        userEmail = extras.getString("userEmail");
        user = new UserProfile(userEmail);
        friendList = user.getFriendList();
        populateListView();
    }


    /**
     * process the list view stuff, show the friends' Emails and usernames
     */
    private void populateListView() {
        String[] friends;
        if (!friendList.isEmpty()) {
            int size = friendList.size();
            friends = new String[size];
            for (int i = 0; i < size; i++) {
                friends[i] = friendList.get(i).toString();
            }
        } else {
            friends = new String[1];
            friends[0] = "";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.friend_item,
                    friends);
        ListView list = (ListView) findViewById(R.id.friends_list_view);
        list.setAdapter(adapter);
    }

    /**
     * turn to the add friend page
     * @param view
     */
    public void addFriendPage(View view) {
        Intent move = new Intent(this, AddFriendActivity.class);
        move.putExtra("userEmail", userEmail);
        startActivity(move);
        finish();
    }
}
