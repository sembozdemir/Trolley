package com.taurus.trolley;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.taurus.trolley.busevents.UserSavedSuccesfullyEvent;
import com.taurus.trolley.domain.Badge;
import com.taurus.trolley.domain.User;
import com.taurus.trolley.helper.FacebookHelper;
import com.taurus.trolley.utils.Constants;
import com.taurus.trolley.utils.DebugUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginButton buttonFacebookLogin;
    private EditText editTextLoginUserName;
    private EditText editTextLoginPassword;
    private EditText editTextSignUpUsername;
    private EditText editTextSignUpPassword;
    private EditText editTexSignUpEmail;
    private Button buttonLogin;
    private Button buttonSignUp;
    private RadioGroup radioGroupSex;
    private SlidingUpPanelLayout slidingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.bus.register(this);
    }

    @Override
    protected void onPause() {
        App.bus.unregister(this);
        super.onPause();
    }

    private void initViews() {
        slidingPanel = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout_login);

        editTextLoginUserName = (EditText) findViewById(R.id.edit_text_username_login);
        editTextLoginPassword = (EditText) findViewById(R.id.edit_text_password_login);
        editTextSignUpUsername = (EditText) findViewById(R.id.edit_text_username_sign_up);
        editTextSignUpPassword = (EditText) findViewById(R.id.edit_text_password_sign_up);
        editTexSignUpEmail = (EditText) findViewById(R.id.edit_text_email_sign_up);

        buttonFacebookLogin = (LoginButton) findViewById(R.id.facebook_login_button);
        buttonFacebookLogin.setOnClickListener(this);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(this);
        buttonSignUp = (Button) findViewById(R.id.button_sign_up);
        buttonSignUp.setOnClickListener(this);

        radioGroupSex = (RadioGroup) findViewById(R.id.radioSex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    private void loginWithFacebook() {
        List<String> permissions = Arrays.asList("public_profile", "email");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user == null) {
                    Log.d(TAG, "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d(TAG, "User signed up and logged in through Facebook!");
                    FacebookHelper.newMeRequest();
                    redirectToMainActivity();
                } else {
                    Log.d(TAG, "User logged in through Facebook!");
                    redirectToMainActivity();
                }
            }
        });
    }

    private void login() {
        String username = editTextLoginUserName.getText().toString();
        String password = editTextLoginPassword.getText().toString();

        if (checkCredentials(username, password)) {
            User.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        // login successful
                        redirectToMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Please check your credentials", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    private void signUp() {
        User user = new User();
        String username = editTextSignUpUsername.getText().toString();
        String password = editTextSignUpPassword.getText().toString();
        String email = editTexSignUpEmail.getText().toString();
        String gender = "";
        if (radioGroupSex.getCheckedRadioButtonId() == R.id.radioMale) {
            gender = Constants.GENDER_MALE;
        } else if (radioGroupSex.getCheckedRadioButtonId() == R.id.radioFemale){
            gender = Constants.GENDER_FEMALE;
        }

        if (checkCredentials(username, email, password, gender)) {
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setGender(gender);
            user.setScore(0);
            user.setBadges(new ArrayList<Badge>());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // signed up succesfully
                        redirectToMainActivity();
                        App.bus.post(new UserSavedSuccesfullyEvent());
                    } else {
                        Toast.makeText(LoginActivity.this, "Could not sign up", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean checkCredentials(String username, String password) {
        if (username.matches("")) {
            Toast.makeText(this, "Username could not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.matches("")) {
            Toast.makeText(this, "Password could not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkCredentials(String username, String email, String password, String gender) {
        if (!checkCredentials(username, password)) {
            return false;
        }
        if (email.matches("")) {
            Toast.makeText(this, "E-mail could not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gender.matches("")) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void redirectToMainActivity() {
        Log.d(TAG, "Redirecting to MainActivity");
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_sign_up:
                signUp();
                break;
            case R.id.button_login:
                login();
                break;
            case R.id.facebook_login_button:
                loginWithFacebook();
        }
    }

    public void newUserForDebug(View view) {
        DebugUtils.DebugUser debugUser = DebugUtils.newUser();
        editTextSignUpUsername.setText(debugUser.username);
        editTexSignUpEmail.setText(debugUser.email);
        editTextSignUpPassword.setText(debugUser.password);
        radioGroupSex.check(R.id.radioMale);
    }

    @Override
    public void onBackPressed() {
        if (!isSignUpPanelCollapsed()) {
            slidingPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return;
        }

        super.onBackPressed();
    }

    private boolean isSignUpPanelCollapsed() {
        return slidingPanel.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }
}
