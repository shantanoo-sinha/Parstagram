package com.shantanoo.parstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Shantanoo on 10/23/2020.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("yAk2OmUu7nACUJwljJNWTNd3He7wDLefmMA9NHnb")
                .clientKey("NmxzRkalvwE8QNdK9aLaXt7CJFOO6n2neXCaDleF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
