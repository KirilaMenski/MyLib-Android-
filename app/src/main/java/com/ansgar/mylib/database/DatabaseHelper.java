package com.ansgar.mylib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.database.entity.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by kirill on 24.1.17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "MyLib.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<User, Integer> mUsersDao = null;
    private Dao<Author, Integer> mAuthorsDao = null;
    private Dao<Book, Integer> mBooksDao = null;
    private Dao<Citation, Integer> mCitationsDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Author.class);
            TableUtils.createTable(connectionSource, Book.class);
            TableUtils.createTable(connectionSource, Citation.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public Dao<User, Integer> getUsersDao() {
        if (mUsersDao == null) {
            try {
                mUsersDao = getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mUsersDao;
    }

    public Dao<Author, Integer> getAuthorsDao() {
        if (mAuthorsDao == null) {
            try {
                mAuthorsDao = getDao(Author.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mAuthorsDao;
    }

    public Dao<Book, Integer> getBooksDao() {
        if (mBooksDao == null) {
            try {
                mBooksDao = getDao(Book.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mBooksDao;
    }

    public Dao<Citation, Integer> getCitationsDao() {
        if (mCitationsDao == null) {
            try {
                mCitationsDao = getDao(Citation.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return mCitationsDao;
    }

}
