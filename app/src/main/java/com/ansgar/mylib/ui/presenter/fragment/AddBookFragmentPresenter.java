package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

/**
 * Created by kirill on 25.1.17.
 */
public class AddBookFragmentPresenter extends BasePresenter {

    private AddBookFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();

    public AddBookFragmentPresenter(AddBookFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void addBook(String coverBookPath, Author author, String bookResPath,
                        String bookTitle, String genre, String series, int seriesNum,
                        int year, String description) {

        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Book book = new Book();
        book.setCover(coverBookPath);
        book.setAuthor(author);
        book.setResPath(bookResPath);
        book.setTitle(bookTitle);
        book.setGenre(genre);
        book.setSeries(series);
        book.setNumSeries(seriesNum);
        book.setYear(year);
        book.setDescription(description);
        book.setUser(user);
        mBookDao.addBook(book);
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
