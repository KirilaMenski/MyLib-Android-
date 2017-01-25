package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.AddAuthorDialogListener;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

/**
 * Created by kirill on 25.1.17.
 */
public class AddAuthorFragmentPresenter extends BasePresenter {

    private AddAuthorFragmentView mView;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();

    public AddAuthorFragmentPresenter(AddAuthorFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void addAuthor(String authorIconPath, String firstName, String lastName, String date, String biography) {
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Author author = new Author();
        author.setCover(authorIconPath);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDate(date);
        author.setBiography(biography);
        author.setUser(user);
        mAuthorDao.addAuthor(author);
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
