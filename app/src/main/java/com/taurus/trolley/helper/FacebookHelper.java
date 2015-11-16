package com.taurus.trolley.helper;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.taurus.trolley.App;
import com.taurus.trolley.busevents.UserSavedSuccesfullyEvent;
import com.taurus.trolley.domain.Badge;
import com.taurus.trolley.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by semih on 08.11.2015.
 */
public class FacebookHelper {
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String BIRTHDAY = "birthday";
    private static final String GENDER = "gender";
    private static final String TAG = FacebookHelper.class.getSimpleName();

    public static void newMeRequest() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if (object != null) {
                            try {
                                String firstName = object.getString(FIRST_NAME);
                                String lastName = object.getString(LAST_NAME);
                                //String birthDay = object.getString(BIRTHDAY);
                                //DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                                //Date dateBirthDay = format.parse(birthDay);
                                String gender = object.getString(GENDER);

                                User.currentUser().setFirstName(firstName);
                                User.currentUser().setLastName(lastName);
                                //User.currentUser().setBirthDay(dateBirthDay);
                                User.currentUser().setGender(gender);
                                User.currentUser().setScore(0);
                                User.currentUser().setBadges(new ArrayList<Badge>());
                                User.currentUser().saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            // send user saved succesfully event
                                            App.bus.post(new UserSavedSuccesfullyEvent());
                                        }
                                    }
                                });

                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,gender,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
