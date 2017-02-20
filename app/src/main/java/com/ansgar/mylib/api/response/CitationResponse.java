package com.ansgar.mylib.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kirila on 20.2.17.
 */

public class CitationResponse implements Serializable{

    @SerializedName("id")
    private long mId;
    @SerializedName("citation")
    private String mCitation;
    @SerializedName("liked")
    private int mLiked;
    @SerializedName("date")
    private String mDate;
    @SerializedName("hasSynchronized")
    private int mHasSynchronized;

    public CitationResponse() {

    }

    public CitationResponse(long id, String citation, int liked, String date, int hasSynchronized) {
        mId = id;
        mCitation = citation;
        mLiked = liked;
        mDate = date;
        mHasSynchronized = hasSynchronized;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getCitation() {
        return mCitation;
    }

    public void setCitation(String citation) {
        mCitation = citation;
    }

    public int getLiked() {
        return mLiked;
    }

    public void setLiked(int liked) {
        mLiked = liked;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int isHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

}
