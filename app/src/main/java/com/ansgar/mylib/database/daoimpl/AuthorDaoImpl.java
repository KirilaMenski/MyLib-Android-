package com.ansgar.mylib.database.daoimpl;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.util.MyLibPreference;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by kirill on 24.1.17.
 */

public class AuthorDaoImpl implements AuthorDao {

    private static AuthorDaoImpl mInstance;
    private WeakReference<Context> mContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<Author, Integer> mDao;

    public static synchronized AuthorDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new AuthorDaoImpl();
        }
        return mInstance;
    }

    private AuthorDaoImpl() {
        mContext = new WeakReference<>(MyLibApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getAuthorsDao();
    }

    @Override
    public void addAuthor(Author author) {
        try {
            mDao.create(author);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAuthor(Author author) {
        try {
            mDao.update(author);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAuthor(Author author) {
        try {
            mDao.delete(author);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author getAuthorById(long id) {
        try {
            QueryBuilder<Author, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("id", id);
            List<Author> authors = queryBuilder.query();
            return (authors.size() > 0) ? authors.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author getAuthorByUuid(String uuid) {
        try {
            QueryBuilder<Author, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("uuid", uuid);
            List<Author> authors = queryBuilder.query();
            return (authors.size() > 0) ? authors.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            QueryBuilder<Author, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("user_id", MyLibPreference.getUserId());
            authors = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public Observable<List<Author>> getUserAuthors() {
        return Observable.create(new Observable.OnSubscribe<List<Author>>() {
            @Override
            public void call(Subscriber<? super List<Author>> subscriber) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }
                try {
                    QueryBuilder<Author, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("user_id", MyLibPreference.getUserId());
                    List<Author> authors = queryBuilder.query();
                    subscriber.onNext(authors);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }
}
