package com.ansgar.mylib.api.response;

import com.ansgar.mylib.util.BitmapCover;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;

/**
 * Created by kirila on 20.2.17.
 */

public class AuthorResponse implements Serializable {

    @SerializedName("id")
    private int mId;
    @SerializedName("coverBytes")
    private String mCoverBytes;
    @SerializedName("firstName")
    private String mFirstName;
    @SerializedName("lastName")
    private String mLastName;
    @SerializedName("biography")
    private String mBiography;
    @SerializedName("date")
    private String mDate;
    @SerializedName("hasSynchronized")
    private int mHasSynchronized;
    @SerializedName("books")
    private List<BookResponse> mAuthorBooks;

    public AuthorResponse() {

    }

    public AuthorResponse(int id, String firstName, String lastName, String biography, String date,
                          int hasSynchronized, List<BookResponse> authorBooks) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mBiography = biography;
        mDate = date;
        mHasSynchronized = hasSynchronized;
        mAuthorBooks = authorBooks;
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

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getCoverBytes() {
        return mCoverBytes;
    }

    public void setCoverBytes(String coverBytes) {
        mCoverBytes = coverBytes;
    }

    public Bitmap getBitmap() {
        return BitmapCover.getBitmapCover(mCoverBytes);
    }

    public int isHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

    public List<BookResponse> getAuthorBooks() {
        return mAuthorBooks;
    }

    public void setAuthorBooks(List<BookResponse> authorBooks) {
        mAuthorBooks = authorBooks;
    }
}
