package com.ansgar.mylib.database.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */


@DatabaseTable(tableName = "authors")
public class Author implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @DatabaseField(columnName = "cover")
    private String mCover;
    @DatabaseField(columnName = "first_name")
    private String mFirstName;
    @DatabaseField(columnName = "last_name")
    private String mLastName;
    @DatabaseField(columnName = "biography")
    private String mBiography;
    @DatabaseField(columnName = "date")
    private String mDate;
    @ForeignCollectionField
    private ForeignCollection<Book> mBooks;

    public Author() {

    }

    public Author(long id, String firstName, String lastName, String biography, String date) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mBiography = biography;
        mDate = date;
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
}
