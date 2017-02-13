package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.SelectEntityDialogView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 25.1.17.
 */
public class SelectEntityDialogPresenter extends BasePresenter {

    public static final String AUTHORS = "authors";
    public static final String BOOKS = "books";

    private SelectEntityDialogView mView;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private List<Book> mAllBooks = new ArrayList<>();
    private List<Author> mAllAuthors = new ArrayList<>();

    public SelectEntityDialogPresenter(SelectEntityDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadEntity(String type) {
        if (type.equals(AUTHORS)) {
            Observable<List<Author>> observable = mAuthorDao.getUserAuthors();
            Observer<List<Author>> observer = new Observer<List<Author>>() {
                @Override
                public void onCompleted() {
                    mView.setAuthorsAdapter(mAllAuthors);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<Author> authors) {
                    mAllAuthors = authors;
                }
            };
            bindObservable(observable, observer);
        }
        if (type.equals(BOOKS)) {
            Observable<List<Book>> observable = mBookDao.getUserBooksFromReadingList(false);
            Observer<List<Book>> observer = new Observer<List<Book>>() {
                @Override
                public void onCompleted() {
                    mView.setBooksAdapter(mAllBooks);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<Book> books) {
                    mAllBooks = books;
                }
            };
            bindObservable(observable, observer);
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
