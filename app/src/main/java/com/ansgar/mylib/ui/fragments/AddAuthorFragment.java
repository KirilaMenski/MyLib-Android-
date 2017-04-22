package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.PhotoDialog;
import com.ansgar.mylib.ui.presenter.fragment.AddAuthorFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;
import com.ansgar.mylib.util.FileManagerUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddAuthorFragment extends BaseFragment implements AddAuthorFragmentView {

    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";

    private AddAuthorFragmentPresenter mPresenter;

    private boolean mEdit;
    private String mCoverPath;

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

    public static AddAuthorFragment newInstance(long authorId) {
        AddAuthorFragment fragment = new AddAuthorFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_AUTHOR, authorId);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_author, null);
        ButterKnife.bind(this, view);
        long author = getArguments().getLong(EXTRA_AUTHOR);
        if (author != -1) {
            mPresenter.initializeView(author);
            mAdd.setText(getString(R.string.edit));
            mEdit = true;
        } else {
            Bitmap image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_default_avatar);
            setCoverPath(FileManagerUtil.defaultImage(image, FileManagerUtil.SD_AUTHORS));
            File file = new File(mCoverPath);
            updatePhotoView(file);
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddAuthorFragmentPresenter.REQUEST_PHOTO && data.getExtras() != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPresenter.updatePhoto(photo);
        }
    }

    @OnClick(R.id.handle_author)
    public void handleAuthor() {
        mPresenter.handleAuthor(mEdit, mFirstName.getText().toString(),
                mLastName.getText().toString(), mDate.getText().toString(), mBiography.getText().toString(), mCoverPath);
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
    public void setCoverPath(String path) {
        mCoverPath = path;
    }

    @Override
    public void setAuthorDate(String date) {
        mDate.setText(date);
    }

    @Override
    public void updatePhotoView(File file) {
        Picasso.with(getContext())
                .load(file)
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.spinner_gray_circle))
                .into(mAuthorIcon);
    }

    @Override
    public void startActivityForResult(Intent intent) {
        startActivityForResult(intent, AddAuthorFragmentPresenter.REQUEST_PHOTO);
    }
}
