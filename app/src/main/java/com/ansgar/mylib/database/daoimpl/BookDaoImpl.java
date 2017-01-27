package com.ansgar.mylib.database.daoimpl;

import android.content.Context;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.util.MyLibPreference;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public class BookDaoImpl implements BookDao {

    private static BookDaoImpl mInstance;
    private WeakReference<Context> mContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<Book, Integer> mDao;

    public static synchronized BookDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new BookDaoImpl();
        }
        return mInstance;
    }

    private BookDaoImpl() {
        mContext = new WeakReference<>(MyLibApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getBooksDao();
    }

    @Override
    public void addBook(Book book) {
        try {
            mDao.create(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBook(Book book) {
        try {
            mDao.update(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBook(Book book) {
        try {
            mDao.delete(book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            books = mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getByGenre(String genre) {
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("genre", genre);
            List<Book> booksByGenre = queryBuilder.query();
            return booksByGenre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book> getBooksFromReadingList(boolean inList) {
        List<Book> books = new ArrayList<>();
        long userId = MyLibPreference.getUserId();
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("in_list", inList ? 1 : 0).and().eq("user_id", userId);
            books.addAll(queryBuilder.query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooksByReadValue(int read) {
        List<Book> books = new ArrayList<>();
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("was_read", read).and().eq("in_list", read);
            books.addAll(queryBuilder.query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getBookById(long id) {
        try {
            QueryBuilder<Book, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("id", id);
            List<Book> books = queryBuilder.query();
            return (books.size() > 0) ? books.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
