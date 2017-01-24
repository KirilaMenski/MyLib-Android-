package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.ReadingListFragmentView;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public class ReadingListFragmentPresenter extends BasePresenter {

    private ReadingListFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();

    public ReadingListFragmentPresenter(ReadingListFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadList(boolean inList) {

        List<Book> books = mBookDao.getBooksFromReadingList(inList);
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
