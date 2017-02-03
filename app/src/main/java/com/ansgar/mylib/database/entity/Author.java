package com.ansgar.mylib.database.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */


@DatabaseTable(tableName = "authors")
public class Author implements Serializable, Comparator<Author> {

    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @DatabaseField(columnName = "cover_bytes")
    private String mCoverBytes;
    @DatabaseField(columnName = "first_name")
    private String mFirstName;
    @DatabaseField(columnName = "last_name")
    private String mLastName;
    @DatabaseField(columnName = "biography")
    private String mBiography;
    @DatabaseField(columnName = "date")
    private String mDate;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @ForeignCollectionField
    private ForeignCollection<Book> mBooks;

    public Author() {

    }

    public Author(long id, String firstName, String lastName, String biography, String date, User user) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mBiography = biography;
        mDate = date;
        mUser = user;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Book> getAuthorBooks() {
        List<Book> books = new ArrayList<>();
        if (mBooks == null) {
            return books;
        }

        for (Book book : mBooks) {
            books.add(book);
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

    @Override
    public int compare(Author o1, Author o2) {
        return (int) (o2.getId() - o1.getId());
    }
}
