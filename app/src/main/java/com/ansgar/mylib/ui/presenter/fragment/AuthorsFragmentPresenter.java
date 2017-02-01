package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SortDialog;
import com.ansgar.mylib.ui.listener.SortDialogListener;
import com.ansgar.mylib.ui.view.fragment.AuthorsFragmentView;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.Collections;
import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public class AuthorsFragmentPresenter extends BasePresenter implements SortDialogListener{

    private AuthorsFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private AuthorDao mAuthorsDao = AuthorDaoImpl.getInstance();

    public AuthorsFragmentPresenter(AuthorsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadAuthors(int pos) {
        long userId = MyLibPreference.getUserId();
        User user = mUserDao.getUserById(userId);
        List<Author> authors = user.getAuthors();
        switch (pos){
            case 0:
                Collections.sort(authors, new Author(){
                    @Override
                    public int compare(Author o1, Author o2) {
                        String lastName1 = o1.getLastName().toLowerCase().trim();
                        String lastName2 = o2.getLastName().toLowerCase().trim();
                        return lastName1.compareTo(lastName2);
                    }
                });
                break;
            case 1:
                Collections.sort(authors, new Author());
                break;
            case 2:
                Collections.sort(authors, new Author(){
                    @Override
                    public int compare(Author o1, Author o2) {
                        return (o2.getAuthorBooks().size() - o1.getAuthorBooks().size());
                    }
                });
                break;
        }
        if (authors.size() == 0) {
            mView.setLayoutVisibility(true);
        } else {
            mView.setLayoutVisibility(false);
            mView.setAuthorAdapter(authors);
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void sortTypePosition(int pos) {
        loadAuthors(pos);
    }
}
