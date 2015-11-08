package com.taurus.trolley;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by semih on 08.11.2015.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Facebook SDK initializing
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "xS10JGDdNnFE0vb4yaHEkMrBcczWCKAu1yCdw5fD", "CUjI6Hs7GZmKpMMdcYGUjb3TEkTtMI6Xy15IkFfn");
        ParseFacebookUtils.initialize(this);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
