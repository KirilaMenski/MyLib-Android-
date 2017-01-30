package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksFragmentView;

/**
 * Created by kirila on 30.1.17.
 */
public class AuthorBooksFragmentPresenter extends BasePresenter {

    private AuthorBooksFragmentView mView;
    private Author mAuthor;

    public AuthorBooksFragmentPresenter(AuthorBooksFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeViews(Author author) {
        mAuthor = author;
    }

    public void loadAuthorBooks() {
        mView.setAuthorBooksAdapter(mAuthor.getAuthorBooks());
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

}
