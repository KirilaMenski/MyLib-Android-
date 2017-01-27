package com.ansgar.mylib.ui.fragments;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.ansgar.mylib.ui.presenter.fragment.AddAuthorFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;
import com.squareup.picasso.Picasso;

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

    private String mAuthorIconPath;
    private boolean mEdit;

    @BindView(R.id.author_icon)
    ImageView mAuthorIcon;
    @BindView(R.id.author_first_name)
    EditText mFisrtName;
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
        if(author != null) {
            mPresenter.initializeView(author);
            mAdd.setText(getString(R.string.edit));
            mEdit = true;
        }
        return view;
    }

    @OnClick(R.id.handle_author)
    public void handleAuthor() {
        mPresenter.handleAuthor(mEdit, mAuthorIconPath, mFisrtName.getText().toString(),
                mLastName.getText().toString(), mDate.getText().toString(), mBiography.getText().toString());
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
    public void setAuthorImage(String img) {
        byte[] b = new byte[1];
        Drawable image = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
        Picasso.with(getContext())
                .load(img)
                .fit()
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_synchronize))
                .into(mAuthorIcon);
    }

    @Override
    public void setAuthorFirstName(String firstName) {
        mFisrtName.setText(firstName);
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
}
