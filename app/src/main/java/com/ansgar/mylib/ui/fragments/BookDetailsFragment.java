package com.ansgar.mylib.ui.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.BookDetailsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.BookDetailsFragmentView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.1.17.
 */

public class BookDetailsFragment extends BaseFragment implements BookDetailsFragmentView {

    private static final int LAYOUT = R.layout.fragment_book_details;
    private static final String EXTRA_BOOK = "com.ansgar.mylib.ui.fragments.book";

    private BookDetailsFragmentPresenter mPresenter;

    @BindView(R.id.book_cover)
    ImageView mBookCover;
    @BindView(R.id.author_name)
    TextView mAuthorName;
    @BindView(R.id.book_title)
    TextView mBookTitle;
    @BindView(R.id.book_date)
    TextView mBookDate;
    @BindView(R.id.book_genre)
    TextView mBookGenre;
    @BindView(R.id.book_series)
    TextView mBookSeries;
    @BindView(R.id.description)
    TextView mBookDescription;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        Book book = (Book) getArguments().getSerializable(EXTRA_BOOK);
        mPresenter.initializeView(book);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.book_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_book:
                mPresenter.updateBook();
                break;
            case R.id.delete_book:
                mPresenter.deleteBook();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new BookDetailsFragmentPresenter(this);
    }

    @Override
    public void setBookCover(Bitmap cover) {
        mBookCover.setImageBitmap(cover);
    }

    @Override
    public void setAuthorName(String authorName) {
        mAuthorName.setText(authorName);
    }

    @Override
    public void setBookTitle(String title) {
        mBookTitle.setText(title);
    }

    @Override
    public void setBookDate(String date) {
        mBookDate.setText(date);
    }

    @Override
    public void setBookGenre(String genre) {
        mBookGenre.setText(genre);
    }

    @Override
    public void setSeries(String series) {
        mBookSeries.setText(series);
    }

    @Override
    public void setDescription(String description) {
        mBookDescription.setText(description);
    }
}
