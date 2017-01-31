package com.ansgar.mylib.ui.presenter.activity;

import android.widget.Toast;

import com.ansgar.mylib.R;
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
        User user = mUserDao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            MyLibPreference.saveUserId(user.getId());
            mView.getActivity().startActivity(MainActivity.newIntent(mView.getContext(), user.getId()));
            mView.getActivity().finish();
        } else {
            Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.incorrect_value), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
