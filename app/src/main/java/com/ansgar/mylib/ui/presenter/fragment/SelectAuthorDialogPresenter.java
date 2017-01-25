package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.AuthorSelectedListener;
import com.ansgar.mylib.ui.view.dialog.SelectAuthorDialogView;

/**
 * Created by kirill on 25.1.17.
 */
public class SelectAuthorDialogPresenter extends BasePresenter implements AuthorSelectedListener {

    private SelectAuthorDialogView mView;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();

    public SelectAuthorDialogPresenter(SelectAuthorDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadAuthors() {
        mView.setAdapter(mAuthorDao.getAllAuthors());
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void authorSelected(Author author) {

    }
}
