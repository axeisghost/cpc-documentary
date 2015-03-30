package centralcpccommittee.shopwithfriends;


import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

    private EditText mPasswordView, mConfirmedView, mUsernameView;
    private AutoCompleteTextView mEmailView;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmedView = (EditText) findViewById(R.id.register_confirm);
        mUsernameView = (EditText) findViewById(R.id.register_username);

    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    public void registerPressed(@SuppressWarnings("UnusedParameters") View view) {

        attemptRegister();
    }

    void backToWelcome() {
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

    void attemptRegister() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmedView.setError(null);
        mUsernameView.setError(null);



        // Store values at the time of the login attempt.
        String mEmail = mEmailView.getText().toString();
        String mPassword = mPasswordView.getText().toString();
        String mConfirm = mConfirmedView.getText().toString();
        String mUsername = mUsernameView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        dataExchanger recorder = dataExchanger.getInstance();


        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(mEmail)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
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
            if (recorder.registerUser(mEmail, mPassword, mUsername)) {
                recorder.readerClose();
                backToWelcome();
            } else {
                mEmailView.setError(getString(R.string.error_existed_email));
                mEmailView.requestFocus();
            }
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    void exitTheAct() {
        Intent move = new Intent(this, WelcomeActivity.class);
        startActivity(move);
        finish();
    }


    public void cancelPressed(@SuppressWarnings("UnusedParameters") View view) {
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


