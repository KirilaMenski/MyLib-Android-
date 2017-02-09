package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.SortDialogListener;
import com.ansgar.mylib.ui.view.fragment.BooksFragmentView;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kirill on 24.1.17.
 */
public class BooksFragmentPresenter extends BasePresenter implements SortDialogListener {

    private BooksFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private Author mAuthor;

    public BooksFragmentPresenter(BooksFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadBooks(Author author, int pos) {
        mAuthor = author;
        long userId = MyLibPreference.getUserId();
        User user = mUserDao.getUserById(userId);
        List<Book> books;
//        if((author == null)){
//            user.getBooks();
//        } else {
//            books = mBookDao.getBookDataObs(author);
//        }
        books = (author == null) ? user.getBooks() : author.getAuthorBooks();
        switch (pos) {
            case 0:
                Collections.sort(books, new Book() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        String title1 = o1.getTitle().toLowerCase().trim();
                        String title2 = o2.getTitle().toLowerCase().trim();
                        return title1.compareTo(title2);
                    }
                });
                break;
            case 1:
                Collections.sort(books, new Book());
                break;
            case 2:
                Collections.sort(books, new Book() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return (o2.getRating() - o1.getRating());
                    }
                });
                break;
            case 3:
                Collections.sort(books, new Book() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        String genre1 = o1.getGenre().toLowerCase().trim();
                        String genre2 = o2.getGenre().toLowerCase().trim();
                        return genre1.compareTo(genre2);
                    }
                });
                break;
            case 4:
                Collections.sort(books, new Book() {
                    @Override
                    public int compare(Book o1, Book o2) {
                        return (o2.getYear() - o1.getYear());
                    }
                });
                break;
        }
        if (books.size() == 0) {
            mView.setLayoutVisibility(true);
        } else {
            mView.setLayoutVisibility(false);
            mView.setAdapter(books);
        }
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void sortTypePosition(int pos) {
        MyLibPreference.saveBookSortType(pos);
        loadBooks(mAuthor, pos);
    }
}
