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
import com.ansgar.mylib.ui.view.fragment.BooksFragmentView;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public class BooksFragmentPresenter extends BasePresenter {

    private BooksFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();

    public BooksFragmentPresenter(BooksFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadBooks(Author author) {
        long userId = MyLibPreference.getUserId();
        User user = mUserDao.getUserById(userId);
        List<Book> books;
        books = (author == null) ? user.getBooks() : author.getAuthorBooks();
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
}
