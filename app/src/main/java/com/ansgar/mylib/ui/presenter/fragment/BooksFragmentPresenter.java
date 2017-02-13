package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.SortDialogListener;
import com.ansgar.mylib.ui.view.fragment.BooksFragmentView;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */
public class BooksFragmentPresenter extends BasePresenter implements SortDialogListener {

    private BooksFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private int mAuthorId;
    private List<Book> allBooks = new ArrayList<>();

    public BooksFragmentPresenter(BooksFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadBooks(int authorId, final int pos) {
        mAuthorId = authorId;
        mView.setProgressBarVis(true);
        Observable<List<Book>> observable = (authorId == -1) ? mBookDao.getUserBooks() : mBookDao.getUserBooksByAuthorId(authorId);
        Observer<List<Book>> observer = new Observer<List<Book>>() {
            @Override
            public void onCompleted() {
                switch (pos) {
                    case 0:
                        Collections.sort(allBooks, new Book() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                String title1 = o1.getTitle().toLowerCase().trim();
                                String title2 = o2.getTitle().toLowerCase().trim();
                                return title1.compareTo(title2);
                            }
                        });
                        break;
                    case 1:
                        Collections.sort(allBooks, new Book());
                        break;
                    case 2:
                        Collections.sort(allBooks, new Book() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return (o2.getRating() - o1.getRating());
                            }
                        });
                        break;
                    case 3:
                        Collections.sort(allBooks, new Book() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                String genre1 = o1.getGenre().toLowerCase().trim();
                                String genre2 = o2.getGenre().toLowerCase().trim();
                                return genre1.compareTo(genre2);
                            }
                        });
                        break;
                    case 4:
                        Collections.sort(allBooks, new Book() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return (o2.getYear() - o1.getYear());
                            }
                        });
                        break;
                }
                mView.setProgressBarVis(false);
                setVisView();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Book> books) {
                allBooks = books;
            }
        };
        bindObservable(observable, observer);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void sortTypePosition(int pos) {
        MyLibPreference.saveBookSortType(pos);
        loadBooks(mAuthorId, pos);
    }

    private void setVisView() {
        if (allBooks.size() == 0) {
            mView.setLayoutVisibility(true);
        } else {
            mView.setLayoutVisibility(false);
            mView.setAdapter(allBooks);
        }
    }
}
