package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.FileManagerDialog;
import com.ansgar.mylib.ui.dialog.PhotoDialog;
import com.ansgar.mylib.ui.dialog.SelectDialog;
import com.ansgar.mylib.ui.dialog.SelectEntityDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedDialogListener;
import com.ansgar.mylib.ui.listener.SelectDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.AddBookFragmentPresenter;
import com.ansgar.mylib.ui.presenter.fragment.SelectEntityDialogPresenter;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;
import com.ansgar.mylib.util.FileManagerUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddBookFragment extends BaseFragment implements AddBookFragmentView, EntitySelectedDialogListener, SelectDialogListener {

    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";
    private static final String EXTRA_BOOK = "com.ansgar.mylib.ui.fragments.book";

    private AddBookFragmentPresenter mPresenter;

    private String mCoverBookBytes;
    private long mAuthorId;
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

    public static AddBookFragment newInstance(long authorId, long bookId) {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_AUTHOR, authorId);
        args.putLong(EXTRA_BOOK, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        ButterKnife.bind(this, view);
        mAuthorId = getArguments().getLong(EXTRA_AUTHOR);
        long bookId = getArguments().getLong(EXTRA_BOOK);
        if (bookId != -1) {
            mPresenter.initializeView(mAuthorId, bookId);
            mAdd.setText(getString(R.string.edit));
            mIsEdit = true;
        } else {
            Bitmap image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.default_book_image);
            setCoverBytes(FileManagerUtil.defaultImage(image, FileManagerUtil.SD_BOOKS));
            File file = new File(mCoverBookBytes);
            updatePhotoView(file);
        }
        return view;
    }

    @OnClick(R.id.book_cover)
    public void selectPhoto() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setListener(mPresenter);
        dialog.show(getFragmentManager(), "photoDialog");
    }

    @OnClick(R.id.select_author)
    public void selectAuthor() {
        SelectEntityDialog dialog = SelectEntityDialog.newInstance(SelectEntityDialogPresenter.AUTHORS);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectAuthorDialog");
    }

    @OnClick(R.id.book_res_path)
    public void selectFile() {
        FileManagerDialog dialog = new FileManagerDialog(getActivity(), "").setFilter(".*\\.txt");
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

        if (mAuthorId == -1) {
            Toast.makeText(getContext(), getString(R.string.author_not_selected), Toast.LENGTH_SHORT).show();
            return;
        }

        int yearPubl = 0;
        if (mBookPublished.length() != 0)
            yearPubl = Integer.valueOf(mBookPublished.getText().toString().trim());
        int numSeries = 0;
        if (mSeriesNum.length() != 0)
            numSeries = Integer.valueOf(mSeriesNum.getText().toString().trim());

        mPresenter.addBook(mIsEdit, mCoverBookBytes, mAuthorId, mBookResPath.getText().toString().trim(),
                mBookTitle.getText().toString().trim(),
                mOtherGenre ? mGenreOther.getText().toString().trim() : mGenre.getText().toString().trim(),
                mSeries.getText().toString().trim(),
                numSeries,
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
    public void authorSelected(long authorId, String firstName, String lastName) {
        mAuthorId = authorId;
        mAuthorName.setText(firstName + " " + lastName);
    }

    @Override
    public void bookSelected(long bookId) {

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
    public void setCoverBytes(String coverBytes) {
        mCoverBookBytes = coverBytes;
    }

    @Override
    public void setGenreEditVisibility(boolean vis) {
        mGenreOther.setVisibility(vis ? View.VISIBLE : View.GONE);
        mGenreOther.setFocusable(vis);
        mGenreOther.setFocusableInTouchMode(vis);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddBookFragmentPresenter.REQUEST_PHOTO) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPresenter.updatePhoto(photo);
        }
    }

    @Override
    public void setCoverBook(Bitmap bitmap) {
        mCoverBook.setImageBitmap(bitmap);
    }

    @Override
    public void setAuthorName(String authorName) {
        mAuthorName.setText(authorName);
    }

    @Override
    public void setAuthorNameById(String authorName) {
        mSelectAuthor.setVisibility(View.GONE);
        mAuthorName.setText(authorName);
        mAuthorName.setEnabled(false);
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

    @Override
    public void updatePhotoView(File file) {
        Picasso.with(getContext())
                .load(file)
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.spinner_gray_circle))
                .into(mCoverBook);
    }

    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, AddBookFragmentPresenter.REQUEST_PHOTO);
    }
}
