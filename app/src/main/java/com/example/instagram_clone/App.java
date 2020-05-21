package com.example.instagram_clone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DYhNohYjcCkcc85Brqk8TPtGD1osaeZRg2dkekzz")
                .clientKey("BeVHfr47YH3Oo4Wy1WJAMqSatcdBfPGngM9BJALf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
