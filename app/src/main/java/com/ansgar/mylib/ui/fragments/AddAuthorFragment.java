package com.ansgar.mylib.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.PhotoDialog;
import com.ansgar.mylib.ui.presenter.fragment.AddAuthorFragmentPresenter;
import com.ansgar.mylib.ui.presenter.fragment.AddBookFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;
import com.ansgar.mylib.util.PictureUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddAuthorFragment extends BaseFragment implements AddAuthorFragmentView {

    private static final int LAYOUT = R.layout.fragment_add_author;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";

    private AddAuthorFragmentPresenter mPresenter;

    private boolean mEdit;

    @BindView(R.id.author_icon)
    ImageView mAuthorIcon;
    @BindView(R.id.author_first_name)
    EditText mFirstName;
    @BindView(R.id.author_last_name)
    EditText mLastName;
    @BindView(R.id.author_date)
    EditText mDate;
    @BindView(R.id.author_biography)
    EditText mBiography;
    @BindView(R.id.handle_author)
    TextView mAdd;

    public static AddAuthorFragment newInstance(Author author) {
        AddAuthorFragment fragment = new AddAuthorFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        ButterKnife.bind(this, view);
        Author author = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        if (author != null) {
            mPresenter.initializeView(author);
            mAdd.setText(getString(R.string.edit));
            mEdit = true;
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddAuthorFragmentPresenter.REQUEST_PHOTO) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPresenter.updatePhoto(photo);
        }
    }

    @OnClick(R.id.handle_author)
    public void handleAuthor() {
        mPresenter.handleAuthor(mEdit, mFirstName.getText().toString(),
                mLastName.getText().toString(), mDate.getText().toString(), mBiography.getText().toString());
    }

    @OnClick(R.id.author_icon)
    public void selectPhoto() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.setListener(mPresenter);
        dialog.show(getFragmentManager(), "photoDialog");
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AddAuthorFragmentPresenter(this);
    }

    @Override
    public void setScreenTitle(String title) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setScreenTitle(title);
    }

    @Override
    public void setAuthorImage(Bitmap img) {
        mAuthorIcon.setImageBitmap(img);
    }

    @Override
    public void setAuthorFirstName(String firstName) {
        mFirstName.setText(firstName);
    }

    @Override
    public void setAuthorLastName(String lastName) {
        mLastName.setText(lastName);
    }

    @Override
    public void setAuthorBiography(String biography) {
        mBiography.setText(biography);
    }

    @Override
    public void setAuthorDate(String date) {
        mDate.setText(date);
    }

    @Override
    public void updatePhotoView(File file) {
        if (!file.exists()) {
            mAuthorIcon.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_images_200dp));
        } else {
            Bitmap bitmap = PictureUtils.getScaleBitmap(file.getPath(), getActivity());
            mAuthorIcon.setImageBitmap(bitmap);
        }
    }

    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, AddAuthorFragmentPresenter.REQUEST_PHOTO);
    }
}
