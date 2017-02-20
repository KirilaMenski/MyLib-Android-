package com.ansgar.mylib.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public class UserResponse implements Serializable {

    @SerializedName("id")
    private int mId;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("coverBytes")
    private String mCoverBytes;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("hasSynchronized")
    private int mHasSynchronized;
    @SerializedName("authors")
    private List<AuthorResponse> mAuthors;

    public UserResponse() {

    }

    public UserResponse(int id, String firstName, String lastName, String cover, String email, String password, int hasSynchronized, List<AuthorResponse> authors) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mCoverBytes = cover;
        mEmail = email;
        mPassword = password;
        mHasSynchronized = hasSynchronized;
        mAuthors = authors;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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

    public String getCoverBytes() {
        return mCoverBytes;
    }

    public void setCoverBytes(String coverBytes) {
        mCoverBytes = coverBytes;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public int isHasSynchronized() {
        return mHasSynchronized;
    }

    public List<AuthorResponse> getAuthors() {
        return mAuthors;
    }

    public void setAuthors(List<AuthorResponse> authors) {
        mAuthors = authors;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }
}
