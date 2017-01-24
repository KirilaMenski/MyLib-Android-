package com.ansgar.mylib.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kirill on 24.1.17.
 */

public class UserResponse implements Serializable {

    @SerializedName("id")
    private long mId;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("cover")
    private String mCover;

    public UserResponse() {

    }

    public UserResponse(long id, String firstName, String lastName, String cover) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mCover = cover;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }
}
