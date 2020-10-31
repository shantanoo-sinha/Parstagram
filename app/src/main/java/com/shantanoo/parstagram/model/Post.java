package com.shantanoo.parstagram.model;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shantanoo on 10/23/2020.
 */
@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_UPDATED_AT = "updatedAt";

    public static String getKeyObjectId() {
        return KEY_OBJECT_ID;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String parseFormat = "EEE MMM dd hh:mm:ss zzz yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(parseFormat, Locale.ENGLISH);
        sf.setLenient(true);
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();

            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    public static String getSimpleDate(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        String newFormat = "MMMM dd, yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.applyPattern(newFormat);
        String newDate = sf.format(new Date(rawJsonDate));
        return newDate;
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public String getKeyUpdatedAt() {
        return getString(KEY_UPDATED_AT);
    }

    public void setKeyUpdatedAt(String updatedAt) {
        put(KEY_UPDATED_AT, updatedAt);
    }

    public String getTimestamp() {
        String createdAt = getCreatedAt().toString();
        return getSimpleDate(createdAt);
    }

    public String getRelativeTime() {
        String createdAt = getCreatedAt().toString();
        return getRelativeTimeAgo(createdAt);
    }
}
