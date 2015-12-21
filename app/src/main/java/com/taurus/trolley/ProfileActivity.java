package com.taurus.trolley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.utils.BeaconPool;

public class ProfileActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "user";
    private static final String EXTRA_IS_CURRENT_USER = "isCurrentUser";
    private static final String EXTRA_SCORE = "score";
    private static final String EXTRA_FIRST_NAME = "firstName";
    private static final String EXTRA_LAST_NAME = "lastName";
    private Boolean isCurrentUser;
    private String username;
    private String firstName;
    private String lastName;
    private int score;
    private TextView textViewName;
    private ImageView imageViewProfile;
    private TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();
        handleIntent();
        handleViews();
    }

    private void initViews() {
        textViewName = (TextView) findViewById(R.id.text_view_name);
        imageViewProfile = (ImageView) findViewById(R.id.image_view_profile);
        textViewScore = (TextView) findViewById(R.id.text_view_coins);
    }

    private void handleViews() {
        textViewScore.setText(String.valueOf(score));
        if (firstName != null && lastName != null) {
            textViewName.setText(String.format("%s %s", firstName, lastName));
        } else {
            textViewName.setText(username);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (isCurrentUser) {
            getMenuInflater().inflate(R.menu.menu_profile, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                // todo: start settings screen
                break;
            case R.id.action_logout:
                // todo: logout
                logout();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        User.currentUser().logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    BeaconPool.getPool().clear();
                    redirectToLoginActivity();
                } else {
                    // Logout unsuccessful
                    Toast.makeText(ProfileActivity.this, "Logout unsuccessful",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void redirectToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        isCurrentUser = intent.getBooleanExtra(EXTRA_IS_CURRENT_USER, true);
        if (isCurrentUser) {
            username = User.currentUser().getUsername();
            firstName = User.currentUser().getFirstName();
            lastName = User.currentUser().getLastName();
            score = User.currentUser().getScore();
            // todo: other user attributes
        } else {
            username = intent.getStringExtra(EXTRA_USERNAME);
            firstName = intent.getStringExtra(EXTRA_FIRST_NAME);
            lastName = intent.getStringExtra(EXTRA_LAST_NAME);
            score = intent.getIntExtra(EXTRA_SCORE, 1);
            // todo: other user attributes
        }
    }

    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_IS_CURRENT_USER, false);
        intent.putExtra(EXTRA_USERNAME, user.getUsername());
        intent.putExtra(EXTRA_FIRST_NAME, user.getFirstName());
        intent.putExtra(EXTRA_LAST_NAME, user.getLastName());
        intent.putExtra(EXTRA_SCORE, user.getScore());

        return intent;
    }

    public static Intent newIntentForCurrentUser(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra(EXTRA_IS_CURRENT_USER, true);

        return intent;
    }
}
