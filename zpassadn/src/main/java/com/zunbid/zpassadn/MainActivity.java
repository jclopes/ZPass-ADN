package com.zunbid.zpassadn;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends ActionBarActivity {

    private Button mOkButton;
    private Button mClearButton;
    private EditText mUriEditText;
    private EditText mPassEditText;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mPassEditText = (EditText)findViewById(R.id.pass_edittext);
        mUriEditText = (EditText)findViewById(R.id.uri_edittext);
        mUriEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    ClipboardManager cb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    cb.setPrimaryClip(ClipData.newPlainText(
                            "hash",
                            genPass(
                                    mSharedPreferences.getString("pref_salt", ""),
                                    mUriEditText.getText().toString(),
                                    mPassEditText.getText().toString()
                            )
                    ));
                    Toast.makeText(MainActivity.this, R.string.ready_toast, Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

        mOkButton = (Button)findViewById(R.id.ok_button);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate the hash and put it on the clipboard
                ClipboardManager cb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                cb.setPrimaryClip(ClipData.newPlainText(
                        "hash",
                        genPass(
                                mSharedPreferences.getString("pref_salt", ""),
                                mUriEditText.getText().toString(),
                                mPassEditText.getText().toString()
                        )
                ));
                Toast.makeText(MainActivity.this, R.string.ready_toast, Toast.LENGTH_SHORT).show();
            }
        });

        mClearButton = (Button)findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the uri_edittext field
                mUriEditText.setText("", TextView.BufferType.EDITABLE);
                // Clear clipboard
                ClipboardManager cb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                cb.setPrimaryClip(ClipData.newPlainText("hash", ""));

                Toast.makeText(MainActivity.this, R.string.clear_toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            startActivity(settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String genPass(String salt, String input, String pass) {
        final String res = "%s@%s:%s";

        MessageDigest hash = null;
        try {
            hash = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] theDigest = hash.digest(String.format(res, salt, input, pass).getBytes());
        // Convert the hash to base62 so that it becomes a shorter string
        String base64 = Base64.encodeToString(theDigest, 0, 32, 0);
        // Destructively convert base64 to base62.
        // This is ok since we don't care about reverting back to the original string.
        String base62 = base64.replaceAll("\\+", "Z").replaceAll("/", "z").replaceAll("=", "");
        return base62;
    }

}
