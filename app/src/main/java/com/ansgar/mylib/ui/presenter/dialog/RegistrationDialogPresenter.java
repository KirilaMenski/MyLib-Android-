package com.ansgar.mylib.ui.presenter.dialog;

import android.widget.Toast;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.RegistrationDialogView;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.RegularExtentionUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kirill on 24.1.17.
 */
public class RegistrationDialogPresenter extends BasePresenter {

    private RegistrationDialogView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();

    public RegistrationDialogPresenter(RegistrationDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void sendForm(String firstName, String lastName, String email, String password, String confirmPas) {
        if (firstName.isEmpty() || lastName.isEmpty() ||
                email.isEmpty() || !RegularExtentionUtil.checkEmail(email)
                || password.isEmpty() || confirmPas.isEmpty()) {
            Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.incorrect_value), Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(confirmPas)) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            mUserDao.addUser(user);
            MyLibPreference.saveUserId(user.getId());
            mView.getActivity().startActivity(MainActivity.newIntent(mView.getContext()));
            mView.getActivity().finish();
        } else {
            mView.setConfirmPass("");
            mView.setPass("");
            Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.incorrect_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
