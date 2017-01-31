package com.ansgar.mylib.ui.presenter.activity;

import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.activity.MainActivityView;
import com.ansgar.mylib.util.MyLibPreference;

/**
 * Created by kirill on 25.1.17.
 */
public class MainActivityPresenter extends BasePresenter {

    private MainActivityView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();

    public MainActivityPresenter(MainActivityView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView() {
        long userId = MyLibPreference.getUserId();
        User user = mUserDao.getUserById(userId);
        mView.setUserName(user.getFirstName() + " " + user.getLastName());
        mView.setUserAvatar(user.getCover());
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
