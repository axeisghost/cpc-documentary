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


public class AddFriendActivity extends ActionBarActivity {

    private EditText friendEmailView, friendUsernameView;
    private String mEmail;
    private UserProfile user;

    /**
     * default onCreate method for activity, take in the account passed
     * from last activity
     * @param savedInstanceState from last activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Bundle extras = getIntent().getExtras();
        mEmail = extras.getString("userEmail");
        user = new UserProfile(mEmail);
        Log.d("bug exists", mEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        friendEmailView = (EditText) findViewById(R.id.add_friend_email);
        friendUsernameView = (EditText) findViewById(R.id.add_friend_username);

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
     * attempt to find friend
     */
    void attemptFindAndAdd() {

        // Reset errors.
        friendEmailView.setError(null);
        friendUsernameView.setError(null);
        String friendEmail = friendEmailView.getText().toString();
        String friendUsername = friendUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(friendEmail)) {
            friendEmailView.setError(getString(R.string.error_field_required));
            focusView = friendEmailView;
            cancel = true;
        } else if (!isEmailValid(friendEmail)) {
            friendEmailView.setError(getString(R.string.error_invalid_email));
            focusView = friendEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(friendUsername)) {
            friendUsernameView.setError(getString(R.string.error_field_required));
            focusView = friendUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            dataExchanger database = dataExchanger.getInstance();
            if (!database.retrieveEmail(friendEmail)) {
                friendEmailView.setError(getString(R.string.error_email_not_registered));
                focusView = friendEmailView;
                focusView.requestFocus();
            } else {
                UserProfile friendUser = new UserProfile(friendEmail);
                if (!friendUser.getUserName().equals(friendUsername)) {
                    friendUsernameView.setError(getString(R.string.error_email_username_not_match));
                    focusView = friendUsernameView;
                    focusView.requestFocus();
                } else {
                    if (friendEmail.equals(mEmail)) {
                        friendEmailView.setError(getString(R.string.error_add_self_as_friend));
                        focusView = friendEmailView;
                        focusView.requestFocus();
                    } else {
                        int i = user.addFriend(friendEmail, friendUsername);
                       String message = "";
                        if (i == 3) {
                            message = friendUsername +
                                    " is successfully added as your friend!";
                        } else if (i == 2) {
                            message = friendUsername + " is already your friend!";
                        }
                        backToMain(message);
                    }
                }
            }
        }
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
        Intent move = new Intent(this, UserFriendListActivity.class);
        move.putExtra("userEmail", mEmail);
        startActivity(move);
        finish();
    }

    /**
     * check the validity if the email typed in
     * @param email the email that is taken in from UI
     * @return true if the email is valid
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public void addFriendPressed(@SuppressWarnings("UnusedParameters") View view) {
        attemptFindAndAdd();
    }
}
