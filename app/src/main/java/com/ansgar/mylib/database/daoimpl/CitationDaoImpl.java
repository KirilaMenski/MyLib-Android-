package com.ansgar.mylib.database.daoimpl;

import android.content.Context;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.CitationDao;
import com.ansgar.mylib.database.entity.Citation;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
