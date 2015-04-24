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

import centralcpccommittee.shopwithfriends.Presenter.AddFriendPresenter;
import centralcpccommittee.shopwithfriends.Presenter.AddFriendPresenterImpl;


public class AddFriendActivity extends ActionBarActivity implements AddFriendView {

    private EditText friendEmailView;
    private EditText friendUsernameView;
    private View focusView;
    private String mEmail;
    private AddFriendPresenter presenter;

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
     * return method after added friend
     * @param message message that will be shown to user
     */

    public void backToMain(String message) {
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
        overridePendingTransition(R.animator.push_right_in, R.animator.fadeout);
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
        presenter = new AddFriendPresenterImpl(mEmail, friendEmailView.getText().toString(),
                friendUsernameView.getText().toString(), this);
        presenter.attemptFindAndAdd();
    }


    @Override
    public void initializeError() {
        friendEmailView.setError(null);
        friendUsernameView.setError(null);
    }

    @Override
    public void friendEmailRequired() {
        friendEmailView.setError(getString(R.string.error_field_required));
        focusView = friendEmailView;
        focusView.requestFocus();
    }

    public void friendEmailInvalid() {
        friendEmailView.setError(getString(R.string.error_invalid_email));
        focusView = friendEmailView;
        focusView.requestFocus();
    }
    @Override
    public void friendUserNameRequired() {
        friendEmailView.setError(getString(R.string.error_invalid_email));
        focusView = friendEmailView;
        focusView.requestFocus();
    }

    @Override
    public void emailNotRegistered() {
        friendEmailView.setError(getString(R.string.error_email_not_registered));
        focusView = friendEmailView;
        focusView.requestFocus();
    }

    @Override
    public void emailUserNameNoMatch() {
        friendUsernameView.setError(getString(R.string.error_email_username_not_match));
        focusView = friendUsernameView;
        focusView.requestFocus();
    }

    @Override
    public void addYourself() {
        friendEmailView.setError(getString(R.string.error_add_self_as_friend));
        focusView = friendEmailView;
        focusView.requestFocus();
    }

    @Override
    public void notProceed() {
        focusView.requestFocus();
    }
}
