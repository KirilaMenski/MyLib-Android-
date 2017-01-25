package com.ansgar.mylib.database.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by kirill on 24.1.17.
 */

@DatabaseTable(tableName = "citations")
public class Citation implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @DatabaseField(columnName = "citation")
    private String mCitation;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @DatabaseField(columnName = "book_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references books(id) on delete cascade")
    private Book mBook;

    public Citation() {

    }

    public Citation(long id, String citation, Book book, User user) {
        mId = id;
        mCitation = citation;
        mUser = user;
        mBook = book;
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
}
