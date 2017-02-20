package com.ansgar.mylib.api.response;

import com.ansgar.mylib.util.BitmapCover;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;

/**
 * Created by kirila on 20.2.17.
 */

public class BookResponse implements Serializable{

    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("genre")
    private String mGenre;
    @SerializedName("coverBytes")
    private String mCoverBytes;
    @SerializedName("series")
    private String mSeries;
    @SerializedName("numSeries")
    private int mNumSeries;
    @SerializedName("year")
    private int mYear;
    @SerializedName("inList")
    private int mInList;
    @SerializedName("rating")
    private int mRating;
    @SerializedName("wasRead")
    private int mWasRead;
    @SerializedName("hasSynchronized")
    private int mHasSynchronized;
    @SerializedName("citations")
    private List<CitationResponse> mCitations;


    public BookResponse() {

    }

    public BookResponse(int id, String title, String description, String genre, String cover, String series, int numSeries, int year,
                int inList, int rating, int wasRead, int hasSynchronized, List<CitationResponse> citations) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mGenre = genre;
        mCoverBytes = cover;
        mSeries = series;
        mNumSeries = numSeries;
        mYear = year;
        mInList = inList;
        mRating = rating;
        mWasRead = wasRead;
        mHasSynchronized = hasSynchronized;
        mCitations = citations;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getCoverBytes() {
        return mCoverBytes;
    }

    public Bitmap getBitmap() {
        return BitmapCover.getBitmapCover(mCoverBytes);
    }

    public void setCoverBytes(String coverBytes) {
        mCoverBytes = coverBytes;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getInList() {
        return mInList;
    }

    public void setInList(int inList) {
        mInList = inList;
    }

    public int getWasRead() {
        return mWasRead;
    }

    public void setWasRead(int wasRead) {
        mWasRead = wasRead;
    }

    public String getSeries() {
        return mSeries;
    }

    public void setSeries(String series) {
        mSeries = series;
    }

    public int getNumSeries() {
        return mNumSeries;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public void setNumSeries(int numSeries) {
        mNumSeries = numSeries;
    }

    public int isHasSynchronized() {
        return mHasSynchronized;
    }

    public List<CitationResponse> getCitations() {
        return mCitations;
    }

    public void setCitations(List<CitationResponse> citations) {
        mCitations = citations;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

}
