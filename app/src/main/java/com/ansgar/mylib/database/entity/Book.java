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

@DatabaseTable(tableName = "books")
public class Book implements Serializable {

    @DatabaseField(generatedId = true, columnName = "id")
    private long mId;
    @DatabaseField(columnName = "title")
    private String mTitle;
    @DatabaseField(columnName = "description")
    private String mDescription;
    @DatabaseField(columnName = "genre")
    private String mGenre;
    @DatabaseField(columnName = "cover")
    private String mCover;
    @DatabaseField(columnName = "year")
    private int mYear;
    @DatabaseField(columnName = "resource_path")
    private String mResPath;
    @DatabaseField(columnName = "in_list")
    private boolean mInList;
    @DatabaseField(columnName = "was_read")
    private boolean mWasRead;
    @DatabaseField(columnName = "author_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references authors(id) on delete cascade")
    private Author mAuthor;
    @DatabaseField(columnName = "user_id", foreign = true,
            foreignAutoRefresh = true, columnDefinition = "integer references users(id) on delete cascade")
    private User mUser;
    @ForeignCollectionField
    private ForeignCollection<Citation> mCitations;


    public Book() {

    }

    public Book(long id, String title, String description, String genre, String cover, int year, String resPath,
                boolean inList, boolean wasRead, Author author, User user) {
        mId = id;
        mTitle = title;
        mDescription = description;
        mGenre = genre;
        mCover = cover;
        mYear = year;
        mAuthor = author;
        mInList = inList;
        mWasRead = wasRead;
        mResPath = resPath;
        mUser = user;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public List<Citation> getBookCitations() {
        List<Citation> citations = new ArrayList<>();
        if (mCitations == null) return citations;
        for (Citation citation : mCitations) {
            citations.add(citation);
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

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
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

    public boolean isInList() {
        return mInList;
    }

    public void setInList(boolean inList) {
        mInList = inList;
    }

    public boolean isWasRead() {
        return mWasRead;
    }

    public void setWasRead(boolean wasRead) {
        mWasRead = wasRead;
    }
}

