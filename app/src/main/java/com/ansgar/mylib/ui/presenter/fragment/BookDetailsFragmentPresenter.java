package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AddBookFragment;
import com.ansgar.mylib.ui.view.fragment.BookDetailsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;

/**
 * Created by kirill on 30.1.17.
 */
public class BookDetailsFragmentPresenter extends BasePresenter {

    private BookDetailsFragmentView mView;
    private Book mBook;
    private BookDao mBookDao = BookDaoImpl.getInstance();

    public BookDetailsFragmentPresenter(BookDetailsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(Book book) {
        mBook = book;
        mView.setAuthorName(book.getAuthor().getFirstName() + "\n" + book.getAuthor().getLastName());
        mView.setBookCover(book.getCover());
        mView.setBookDate(String.valueOf(book.getYear()));
        mView.setDescription(book.getDescription());
        mView.setBookTitle(book.getTitle());
        mView.setBookGenre(book.getGenre());
        mView.setSeries(book.getSeries() + "-" + book.getNumSeries());
    }

    public void updateBook() {
        FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                R.id.main_fragment_container, AddBookFragment.newInstance(mBook.getAuthor(), mBook),
                true, R.anim.right_out, R.anim.left_out);
    }

    public void deleteBook() {
        mBookDao.deleteBook(mBook);
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
