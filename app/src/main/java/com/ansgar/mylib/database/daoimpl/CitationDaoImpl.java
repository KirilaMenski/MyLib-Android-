package com.ansgar.mylib.database.daoimpl;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.CitationDao;
import com.ansgar.mylib.database.entity.Citation;
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

public class CitationDaoImpl implements CitationDao {

    private static CitationDaoImpl mInstance;
    private WeakReference<Context> mContext;
    private DatabaseHelper mDatabaseHelper;
    private Dao<Citation, Integer> mDao;


    public static synchronized CitationDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new CitationDaoImpl();
        }
        return mInstance;
    }

    private CitationDaoImpl() {
        mContext = new WeakReference<>(MyLibApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getCitationsDao();
    }

    @Override
    public void addCitation(Citation citation) {
        try {
            mDao.create(citation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCitation(Citation citation) {
        try {
            mDao.update(citation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCitation(Citation citation) {
        try {
            mDao.delete(citation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Citation> getAllCitations() {
        List<Citation> citations = new ArrayList<>();
        try {
            citations = mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citations;
    }

    @Override
    public Observable<List<Citation>> getBookCitations(final int bookId) {
        return Observable.create(new Observable.OnSubscribe<List<Citation>>() {
            @Override
            public void call(Subscriber<? super List<Citation>> subscriber) {
                if (subscriber == null) {
                    return;
                }
                try {
                    QueryBuilder<Citation, Integer> queryBuilder = mDao.queryBuilder();
                    queryBuilder.where().eq("book_id", bookId);
                    List<Citation> citations = queryBuilder.query();
                    subscriber.onNext(citations);
                    subscriber.onCompleted();
                } catch (SQLException e) {
                    subscriber.onError(e);
                    e.printStackTrace();
                }
            }
        });
    }
}
