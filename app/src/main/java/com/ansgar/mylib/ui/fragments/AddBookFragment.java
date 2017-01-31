package com.ansgar.mylib.ui.fragments;

import android.graphics.Bitmap;
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
import com.ansgar.mylib.ui.dialog.FileManagerDialog;
import com.ansgar.mylib.ui.dialog.SelectEntityDialog;
import com.ansgar.mylib.ui.dialog.SelectDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedDialogListener;
import com.ansgar.mylib.ui.listener.SelectDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.AddBookFragmentPresenter;
import com.ansgar.mylib.ui.presenter.fragment.SelectEntityDialogPresenter;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;
import com.ansgar.mylib.util.BitmapCover;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddBookFragment extends BaseFragment implements AddBookFragmentView, EntitySelectedDialogListener, SelectDialogListener {

    private static final int LAYOUT = R.layout.fragment_add_book;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";
    private static final String EXTRA_BOOK = "com.ansgar.mylib.ui.fragments.book";

    private AddBookFragmentPresenter mPresenter;

    private String mCoverBookBytes;
    private Author mAuthor;
    private String mType;
    private boolean mOtherGenre;
    private boolean mIsEdit;

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

    public static AddBookFragment newInstance(Author author, Book book) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        args.putSerializable(EXTRA_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        mAuthor = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        Book book = (Book) getArguments().getSerializable(EXTRA_BOOK);
        if (book != null) {
            mPresenter.initializeView(book);
            mIsEdit = true;
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAuthor != null) {
            mSelectAuthor.setVisibility(View.GONE);
            mAuthorName.setText(mAuthor.getFirstName() + "\n" + mAuthor.getLastName());
            mAuthorName.setEnabled(false);
        }
    }

    @OnClick(R.id.select_author)
    public void selectAuthor() {
        SelectEntityDialog dialog = SelectEntityDialog.newInstance(SelectEntityDialogPresenter.AUTHORS);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectAuthorDialog");
    }

    @OnClick(R.id.book_res_path)
    public void selectFile() {
        FileManagerDialog dialog = new FileManagerDialog(getActivity()).setFilter(".*\\.txt");
        dialog.setListener(mPresenter);
        dialog.show();
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
        if (mBookPublished.length() != 0)
            Integer.valueOf(mBookPublished.getText().toString().trim());

        mPresenter.addBook(mIsEdit, mCoverBookBytes, mAuthor, mBookResPath.getText().toString().trim(),
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

    @Override
    public void setCoverBook(String cover) {
        mCoverBookBytes = cover;
        final Bitmap drawable = BitmapCover.getBitmapCover(cover);
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                mCoverBook.setImageBitmap(drawable);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//            }
//        };
//        Picasso.with(getContext())
//                .load(cover)
////                .fit()
////                .centerCrop()
//                .error(ContextCompat.getDrawable(getContext(), R.drawable.ic_synchronize))
//                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_synchronize))
//                .into(target);
        mCoverBook.setImageBitmap(drawable);
    }

    @Override
    public void setAuthorName(String authorName) {
        mAuthorName.setText(authorName);
    }

    @Override
    public void setBookResPath(String path) {
        mBookResPath.setText(path);
    }

    @Override
    public void setBookTitle(String title) {
        mBookTitle.setText(title);
    }

    @Override
    public void setBookPublished(String published) {
        mBookPublished.setText(published);
    }

    @Override
    public void setBookGenre(String genre) {
        mGenre.setText(genre);
    }

    @Override
    public void setSeries(String series) {
        mSeries.setText(series);
    }

    @Override
    public void setNumbSeries(String numbSeries) {
        mSeriesNum.setText(numbSeries);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }
}
