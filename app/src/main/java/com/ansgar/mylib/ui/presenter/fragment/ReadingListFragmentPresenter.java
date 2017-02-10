package com.ansgar.mylib.ui.presenter.fragment;

import android.util.Log;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.ReadingListListener;
import com.ansgar.mylib.ui.view.fragment.ReadingListFragmentView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */
public class ReadingListFragmentPresenter extends BasePresenter implements ReadingListListener {

    private ReadingListFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private List<Book> mAllBooks = new ArrayList<>();

    public ReadingListFragmentPresenter(ReadingListFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadList(boolean inList) {

//        List<Book> books = mBookDao.getBooksFromReadingList(inList);
        Observable<List<Book>> observable = mBookDao.getUserBooksFromReadingList(inList);
        Observer<List<Book>> observer = new Observer<List<Book>>() {
            @Override
            public void onCompleted() {
                if (mAllBooks.size() == 0) {
                    mView.setLayoutVisibility(true);
                } else {
                    mView.setLayoutVisibility(false);
                    mView.setAdapter(mAllBooks);
                }
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
//        bindObservableWithoutComposite(observable, observer);
    }

    public void addBookToList(Book book) {
        book.setInList(1);
        mBookDao.updateBook(book);
        loadList(true);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void changeBookStatus(Book book, boolean wasRead) {
        book.setWasRead(wasRead ? 0 : 1);
        mBookDao.updateBook(book);
        mView.notifyData();
    }
}
