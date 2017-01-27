package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.view.dialog.SelectEntityDialogView;

/**
 * Created by kirill on 25.1.17.
 */
public class SelectEntityDialogPresenter extends BasePresenter {

    public static final String AUTHORS = "authors";
    public static final String BOOKS = "books";

    private SelectEntityDialogView mView;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();

    public SelectEntityDialogPresenter(SelectEntityDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadEntity(String type) {
        if (type.equals(AUTHORS)) mView.setAuthorsAdapter(mAuthorDao.getAllAuthors());
        if(type.equals(BOOKS)) mView.setBooksAdapter(mBookDao.getBooksByReadValue(0));
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
