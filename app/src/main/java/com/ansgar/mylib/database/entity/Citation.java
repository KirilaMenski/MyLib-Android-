package com.ansgar.mylib.database.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kirill on 24.1.17.
 */

@DatabaseTable(tableName = "citations")
public class Citation implements Serializable {

    @SerializedName("id")
    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @SerializedName("citation")
    @DatabaseField(columnName = "citation")
    private String mCitation;
    @SerializedName("liked")
    @DatabaseField(columnName = "liked")
    private int mLiked;
    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String mDate;
    @SerializedName("hasSynchronized")
    @DatabaseField(columnName = "synchronized")
    private boolean mHasSynchronized;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @DatabaseField(columnName = "book_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references books(id) on delete cascade")
    private Book mBook;

    public Citation() {

    }

    public Citation(long id, String citation, int liked, String date, Book book, User user, boolean hasSynchronized) {
        mId = id;
        mCitation = citation;
        mLiked = liked;
        mDate = date;
        mUser = user;
        mBook = book;
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

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Book getBook() {
        return mBook;
    }

    public void setBook(Book book) {
        mBook = book;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(boolean hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }
}
