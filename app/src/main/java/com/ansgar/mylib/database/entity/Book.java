package com.ansgar.mylib.database.entity;

import com.ansgar.mylib.util.BitmapCover;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;

import android.graphics.Bitmap;

/**
 * Created by kirill on 24.1.17.
 */

@DatabaseTable(tableName = "books")
public class Book implements Serializable, Comparator<Book> {

    @DatabaseField(generatedId = true, columnName = "id")
    private int mId;
    @DatabaseField(columnName = "title")
    private String mTitle;
    @DatabaseField(columnName = "description")
    private String mDescription;
    @DatabaseField(columnName = "genre")
    private String mGenre;
    @DatabaseField(columnName = "cover_bytes")
    private String mCoverBytes;
    @DatabaseField(columnName = "series")
    private String mSeries;
    @DatabaseField(columnName = "num_series")
    private int mNumSeries;
    @DatabaseField(columnName = "year")
    private int mYear;
    @DatabaseField(columnName = "resource_path")
    private String mResPath;
    @DatabaseField(columnName = "in_list")
    private int mInList;
    @DatabaseField(columnName = "rating")
    private int mRating;
    @DatabaseField(columnName = "was_read")
    private int mWasRead;
    @DatabaseField(columnName = "synchronized")
    private boolean mHasSynchronized;
    @DatabaseField(columnName = "author_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references authors(id) on delete cascade")
    private Author mAuthor;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
//    @ForeignCollectionField
//    private ForeignCollection<Citation> mCitations;


    public Book() {

    }

    public Book(int id, String title, String description, String genre, String cover, String series, int numSeries, int year, String resPath,
                int inList, int rating, int wasRead, Author author, User user, boolean hasSynchronized) {
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
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

//    public List<Citation> getBookCitations() {
//        List<Citation> citations = new ArrayList<>();
//        if (mCitations == null) return citations;
//        for (Citation citation : mCitations) {
//            citations.add(citation);
//        }
//        return citations;
//    }

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

    public boolean isHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(boolean hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

    @Override

    public int compare(Book o1, Book o2) {
        return o2.getId() - o1.getId();
    }
}

