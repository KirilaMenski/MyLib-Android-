package com.ansgar.mylib.database.entity;

import com.ansgar.mylib.util.BitmapCover;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.graphics.Bitmap;

/**
 * Created by kirill on 24.1.17.
 */

@DatabaseTable(tableName = "books")
public class Book implements Serializable, Comparator<Book> {

    @SerializedName("id")
    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @SerializedName("title")
    @DatabaseField(columnName = "title")
    private String mTitle;
    @SerializedName("description")
    @DatabaseField(columnName = "description")
    private String mDescription;
    @SerializedName("genre")
    @DatabaseField(columnName = "genre")
    private String mGenre;
    @SerializedName("coverBytes")
    @DatabaseField(columnName = "cover_bytes")
    private String mCoverBytes;
    @SerializedName("series")
    @DatabaseField(columnName = "series")
    private String mSeries;
    @SerializedName("numSeries")
    @DatabaseField(columnName = "num_series")
    private int mNumSeries;
    @SerializedName("year")
    @DatabaseField(columnName = "year")
    private int mYear;
    @DatabaseField(columnName = "resource_path")
    private String mResPath;
    @SerializedName("inList")
    @DatabaseField(columnName = "in_list")
    private int mInList;
    @SerializedName("rating")
    @DatabaseField(columnName = "rating")
    private int mRating;
    @SerializedName("wasRead")
    @DatabaseField(columnName = "was_read")
    private int mWasRead;
    @SerializedName("hasSynchronized")
    @DatabaseField(columnName = "synchronized")
    private int mHasSynchronized;
    @DatabaseField(columnName = "author_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references authors(id) on delete cascade")
    private Author mAuthor;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @ForeignCollectionField
    private ForeignCollection<Citation> mCitationsList;
    @SerializedName("citations")
    private List<Citation> mCitations;


    public Book() {

    }

    public Book(long id, String title, String description, String genre, String cover, String series, int numSeries, int year, String resPath,
                int inList, int rating, int wasRead, Author author, User user, int hasSynchronized, List<Citation> citations) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mGenre = genre;
        mCoverBytes = cover;
        mSeries = series;
        mNumSeries = numSeries;
        mYear = year;
        mAuthor = author;
        mInList = inList;
        mRating = rating;
        mWasRead = wasRead;
        mResPath = resPath;
        mUser = user;
        mHasSynchronized = hasSynchronized;
        mCitations = citations;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Citation> getBookCitations() {
        mCitations = new ArrayList<>();
        for (Citation citation : mCitationsList) {
            mCitations.add(citation);
        }
        return mCitations;
    }

    public List<Citation> getUnSynchronizedCitations() {
        List<Citation> citations = new ArrayList<>();
        for (Citation citation : mCitationsList) {
            if (citation.getHasSynchronized() == 0) {
                citations.add(citation);
            }
        }
        return citations;
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

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getResPath() {
        return mResPath;
    }

    public void setResPath(String resPath) {
        mResPath = resPath;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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

    public String getPhotoFileName() {
        return "IMG_" + String.valueOf(getId()) + ".jpg";
    }

    public int getHasSynchronized() {
        return mHasSynchronized;
    }

    public List<Citation> getCitations() {
        return mCitations;
    }

    public void setCitations(List<Citation> citations) {
        mCitations = citations;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

    @Override
    public int compare(Book o1, Book o2) {
        return (int) (o2.getId() - o1.getId());
    }
}

