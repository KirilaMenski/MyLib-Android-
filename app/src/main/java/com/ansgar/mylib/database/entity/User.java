package com.ansgar.mylib.database.entity;

import android.graphics.Bitmap;

import com.ansgar.mylib.util.BitmapCover;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

@DatabaseTable(tableName = "users")
public class User implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private int mId;
    @DatabaseField(columnName = "first_name")
    private String mFirstName;
    @DatabaseField(columnName = "last_name")
    private String mLastName;
    @DatabaseField(columnName = "cover_bytes")
    private String mCoverBytes;
    @DatabaseField(columnName = "email")
    private String mEmail;
    @DatabaseField(columnName = "password")
    private String mPassword;
    @DatabaseField(columnName = "synchronized")
    private boolean mHasSynchronized;
    @ForeignCollectionField
    ForeignCollection<Author> mAuthors;
    @ForeignCollectionField
    ForeignCollection<Book> mBooks;

    public User() {

    }

    public User(int id, String firstName, String lastName, String cover, String email, String password, boolean hasSynchronized) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mCoverBytes = cover;
        mEmail = email;
        mPassword = password;
        mHasSynchronized = hasSynchronized;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        if (mAuthors == null) {
            return authors;
        }
        for (Author author : mAuthors) {
            authors.add(author);
        }
        return authors;
    }

    public List<Book> getBooks() {
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

    public Bitmap getBitmap() {
        return BitmapCover.getBitmapCover(mCoverBytes);
    }

    public boolean isHasSynchronized() {
        return mHasSynchronized;
    }

    public void setHasSynchronized(boolean hasSynchronized) {
        mHasSynchronized = hasSynchronized;
    }
}
