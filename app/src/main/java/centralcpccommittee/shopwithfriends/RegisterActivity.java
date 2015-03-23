package centralcpccommittee.shopwithfriends;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

    private EditText mPasswordView, mConfirmedView, mUsernameView;
    private AutoCompleteTextView mEmailView;
    private Button registerButton;


    private static final String FIREBASE_URL = "https://shining-heat-1001.firebaseio.com";
    private Firebase mFirebaseRef = new Firebase(FIREBASE_URL);
    private Firebase userRef;
    private String mEmail;
    private String mPassword;
    private String mConfirm;
    private String mUsername;
    private User userBuffer;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmedView = (EditText) findViewById(R.id.register_confirm);
        mUsernameView = (EditText) findViewById(R.id.register_username);

        registerButton = (Button) findViewById(R.id.register_button);

    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    public void registerPressed(View view) {
        attemptRegister();
    }

    public void backToWelcome() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this).
                        setMessage(getString(R.string.Hint_register_successfully)).
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                exitTheAct();
                            }
                        });
        builder.create().show();
    }

    public void attemptRegister() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmedView.setError(null);
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        mConfirm = mConfirmedView.getText().toString();
        mUsername = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(mEmail)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (emailHasIllegalChar(mEmail)) {
            mEmailView.setError("Email can't contain $ # [ ] /");
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(mUsername)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!TextUtils.isEmpty(mPassword) && !isPasswordValid(mPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!TextUtils.isEmpty(mConfirm) && !isPasswordValid(mConfirm)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfirmedView;
            cancel = true;
        } else if (!(mConfirm.equals(mPassword))) {
            mPasswordView.setError(getString(R.string.error_unmatched_passwords));
            focusView = mPasswordView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            //focusView1.requestFocus();
        } else {
            mEmail = replaceDot(mEmail);
            userRef = mFirebaseRef.child(mEmail);
            registerUser();

        }
    }

    private void registerUser() {
        this.userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // is the user email doesn't exist
                if (snapshot.getValue() == null) {
                    userBuffer = new User(RegisterActivity.this.mUsername, RegisterActivity.this.mPassword, RegisterActivity.this.mEmail);
                    RegisterActivity.this.userRef.setValue(userBuffer);
                    Log.d("register", "new user registered");
                    RegisterActivity.this.backToWelcome();
                } else {
                    RegisterActivity.this.mEmailView.setError(getString(R.string.error_existed_email));
                    RegisterActivity.this.mEmailView.requestFocus();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /**
     * A helper method to replace the "." in email to "<dot>", firebase doesn't accept "." as key
     * @param email
     * @return email address with no "."
     */
    public static String replaceDot(String email) {
        return email.replaceAll("\\.", "<dot>");
    }
    public static String replaceBack(String email) {return email.replaceAll("(dot)", ".");}
    private boolean emailHasIllegalChar(String email) {
        return ((email.contains("[")) || (email.contains("]")) || (email.contains("/")) ||
                (email.contains("#")) || (email.contains("$")));
    }
    private boolean isEmailValid(String email) {

        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void exitTheAct() {
        Intent move = new Intent(this, WelcomeActivity.class);
        startActivity(move);
        finish();
    }


    public void cancelPressed(View view) {
        exitTheAct();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}


