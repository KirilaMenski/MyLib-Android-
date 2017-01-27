package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SelectEntityDialog;
import com.ansgar.mylib.ui.dialog.SelectDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedDialogListener;
import com.ansgar.mylib.ui.listener.SelectDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.AddBookFragmentPresenter;
import com.ansgar.mylib.ui.presenter.fragment.SelectEntityDialogPresenter;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddBookFragment extends BaseFragment implements AddBookFragmentView, EntitySelectedDialogListener, SelectDialogListener {

    private static final int LAYOUT = R.layout.fragment_add_book;

    private AddBookFragmentPresenter mPresenter;

    private String mCoverBookPath;
    private Author mAuthor;
    private String mType;
    private boolean mOtherGenre;

    @BindView(R.id.book_cover)
    ImageView mCoverBook;
    @BindView(R.id.select_author)
    ImageView mSelectAuthor;
    @BindView(R.id.author_name)
    EditText mAuthorName;
    @BindView(R.id.book_res_path)
    TextView mBookResPath;
    @BindView(R.id.book_title)
    EditText mBookTitle;
    @BindView(R.id.book_date)
    EditText mBookPublished;
    @BindView(R.id.book_genre)
    TextView mGenre;
    @BindView(R.id.book_genre_edit)
    EditText mGenreOther;
    @BindView(R.id.book_series)
    EditText mSeries;
    @BindView(R.id.book_series_numb)
    TextView mSeriesNum;
    @BindView(R.id.book_description)
    EditText mDescription;
    @BindView(R.id.add_book)
    TextView mAdd;

    public static AddBookFragment newInstance() {
        AddBookFragment fragment = new AddBookFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.select_author)
    public void selectAuthor() {
        SelectEntityDialog dialog = SelectEntityDialog.newInstance(SelectEntityDialogPresenter.AUTHORS);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectAuthorDialog");
    }

    @OnClick(R.id.book_genre)
    public void selectGenre() {
        mType = SelectDialog.GENRE_TYPE;
        SelectDialog dialog = SelectDialog.newInstance(mType);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectDialogGenre");
    }

    @OnClick(R.id.book_series_numb)
    public void selectSeriesNimb() {
        mType = SelectDialog.INT_TYPE;
        SelectDialog dialog = SelectDialog.newInstance(mType);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectDialogNumb");
    }

    @OnClick(R.id.add_book)
    public void addBook() {

        if (mAuthor == null) {
            Toast.makeText(getContext(), "You don't select author", Toast.LENGTH_SHORT).show();
            return;
        }

        int yearPubl = 0;
        if (mBookPublished.length() != 0) Integer.valueOf(mBookPublished.getText().toString().trim());

        mPresenter.addBook(mCoverBookPath, mAuthor, mBookResPath.getText().toString().trim(),
                mBookTitle.getText().toString().trim(),
                mOtherGenre ? mGenreOther.getText().toString().trim() : mGenre.getText().toString().trim(),
                mSeries.getText().toString().trim(),
                Integer.valueOf(mSeriesNum.getText().toString().trim()),
                yearPubl,
                mDescription.getText().toString());
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AddBookFragmentPresenter(this);
    }

    @Override
    public void authorSelected(Author author) {
        mAuthor = author;
        mAuthorName.setText(mAuthor.getFirstName() + " " + mAuthor.getLastName());
    }

    @Override
    public void bookSelected(Book book) {

    }

    @Override
    public void itemSelected(String value, boolean other) {
        if (mType.equals(SelectDialog.GENRE_TYPE)) {
            if (!other) mGenre.setText(value);
            setGenreEditVisibility(other);
        }
        if (mType.equals(SelectDialog.INT_TYPE)) {
            mSeriesNum.setText(value);
        }
        mOtherGenre = other;
    }

    @Override
    public void setGenreEditVisibility(boolean vis) {
        mGenreOther.setVisibility(vis ? View.VISIBLE : View.GONE);
        mGenreOther.setFocusable(vis);
        mGenreOther.setFocusableInTouchMode(vis);
    }
}
