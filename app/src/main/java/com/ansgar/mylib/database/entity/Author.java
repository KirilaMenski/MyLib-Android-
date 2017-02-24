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


@DatabaseTable(tableName = "authors")
public class Author implements Serializable, Comparator<Author> {

    @SerializedName("id")
    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @SerializedName("coverBytes")
    @DatabaseField(columnName = "cover_bytes")
    private String mCoverBytes;
    @SerializedName("firstName")
    @DatabaseField(columnName = "first_name")
    private String mFirstName;
    @SerializedName("lastName")
    @DatabaseField(columnName = "last_name")
    private String mLastName;
    @SerializedName("biography")
    @DatabaseField(columnName = "biography")
    private String mBiography;
    @SerializedName("date")
    @DatabaseField(columnName = "date")
    private String mDate;
    @SerializedName("hasSynchronized")
    @DatabaseField(columnName = "synchronized")
    private int mHasSynchronized;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @ForeignCollectionField
    private ForeignCollection<Book> mBooks;
    @SerializedName("books")
    private List<Book> mAuthorBooks;

    public Author() {

    }

    public Author(long id, String firstName, String lastName, String biography, String date, User user, int hasSynchronized, List<Book> authorBooks) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mBiography = biography;
        mDate = date;
        mUser = user;
        mHasSynchronized = hasSynchronized;
        mAuthorBooks = authorBooks;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Book> getBooks() {
        mAuthorBooks = new ArrayList<>();
        for (Book book : mBooks) {
            mAuthorBooks.add(book);
        }
        return mAuthorBooks;
    }

    public List<Book> getUnSynchronizedBooks() {
        List<Book> books = new ArrayList<>();
        for (Book book : mBooks) {
            if (book.getHasSynchronized() == 0) {
                books.add(book);
            }
        }
        return books;
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

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
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

    public int getHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(int hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }

    public void setAuthorBooks(List<Book> authorBooks) {
        mAuthorBooks = authorBooks;
    }

    public List<Book> getAuthorBooks() {
        return mAuthorBooks;
    }

    @Override
    public int compare(Author o1, Author o2) {
        return (int) (o2.getId() - o1.getId());
    }
}
