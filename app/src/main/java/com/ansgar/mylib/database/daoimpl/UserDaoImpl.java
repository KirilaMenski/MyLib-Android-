package com.ansgar.mylib.database.daoimpl;

import android.content.Context;

import com.ansgar.mylib.MyLibApp;
import com.ansgar.mylib.database.DatabaseHelper;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.entity.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl mInstance;
    private DatabaseHelper mDatabaseHelper;
    private WeakReference<Context> mContext;
    private Dao<User, Integer> mDao;

    public static synchronized UserDaoImpl getInstance() {
        if (mInstance == null) {
            mInstance = new UserDaoImpl();
        }
        return mInstance;
    }

    private UserDaoImpl() {
        mContext = new WeakReference<>(MyLibApp.getAppContext());
        mDatabaseHelper = OpenHelperManager.getHelper(mContext.get(), DatabaseHelper.class);
        mDao = mDatabaseHelper.getUsersDao();
    }

    @Override
    public void addUser(User user) {
        try {
            mDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            mDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            mDao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(long id) {
        try {
            QueryBuilder<User, Integer> queryBuilder = mDao.queryBuilder();
            queryBuilder.where().eq("id", id);
            List<User> users = queryBuilder.query();
            return (users.size() > 0) ? users.get(0) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
