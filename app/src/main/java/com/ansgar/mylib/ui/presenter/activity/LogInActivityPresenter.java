package com.ansgar.mylib.ui.presenter.activity;

import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.activity.LogInActivityView;
import com.ansgar.mylib.util.MyLibPreference;

/**
 * Created by kirill on 24.1.17.
 */
public class LogInActivityPresenter extends BasePresenter {

    private LogInActivityView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();

    public LogInActivityPresenter(LogInActivityView view) {
        super(view.getContext());
        mView = view;
    }

    public void login(String email, String password) {
        if (email.length() == 0 || password.length() == 0) return;

        //TODO
//        UserResponse userResponse = new UserResponse(1, "Kirila", "Menski", "default");
        User user = new User(1, "Kirila", "Menski", "default");
        MyLibPreference.saveUserId(user.getId());
        mUserDao.addUser(user);

        mView.getActivity().startActivity(MainActivity.newIntent(mView.getContext(), user.getId()));
        mView.getActivity().finish();
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
