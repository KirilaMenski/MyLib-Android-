package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AddBookFragment;
import com.ansgar.mylib.ui.listener.RatingAdapterListener;
import com.ansgar.mylib.ui.view.fragment.BookDetailsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;

import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 30.1.17.
 */
public class BookDetailsFragmentPresenter extends BasePresenter implements RatingAdapterListener {

    private BookDetailsFragmentView mView;
    private Book mBook;
    private BookDao mBookDao = BookDaoImpl.getInstance();

    public BookDetailsFragmentPresenter(BookDetailsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(int bookId) {
        mBook = mBookDao.getBookById(bookId);
        mView.setScreenTitle(mBook.getTitle());
        mView.setAuthorName(mBook.getAuthor().getFirstName() + "\n" + mBook.getAuthor().getLastName());
        mView.setBookCover(mBook.getBitmap());
        mView.setBookDate(String.valueOf(mBook.getYear()));
        mView.setDescription(mBook.getDescription());
        mView.setBookTitle(mBook.getTitle());
        mView.setBookGenre(mBook.getGenre());
        mView.setSeries(mBook.getSeries() + "-" + mBook.getNumSeries());
        mView.setRatingAdapter(mBook.getRating());
    }

    public void updateBook() {
        FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                R.id.main_fragment_container, AddBookFragment.newInstance(mBook.getAuthor().getId(), mBook.getId()),
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

    @Override
    public void rating(int rating) {
        mBook.setRating(rating);
        mBookDao.updateBook(mBook);
        mView.setRatingAdapter(rating);
    }
}
