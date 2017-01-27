package com.ansgar.mylib.ui.presenter.fragment;

import android.util.Log;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorsFragmentView;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public class AuthorsFragmentPresenter extends BasePresenter {

    private AuthorsFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private AuthorDao mAuthorsDao = AuthorDaoImpl.getInstance();

    public AuthorsFragmentPresenter(AuthorsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadAuthors() {
        long userId = MyLibPreference.getUserId();
        User user = mUserDao.getUserById(userId);
        List<Author> authors = user.getAuthors();
        if (authors.size() == 0) {
            mView.setLayoutVisibility(true);
        } else {
            mView.setLayoutVisibility(false);
            mView.setAuthorAdapter(authors);
        }
        for(int i = 0; i < user.getBooks().size(); i++){
            Log.i("!!!!!!!!!!", "title: " + user.getBooks().get(i).getTitle());
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
