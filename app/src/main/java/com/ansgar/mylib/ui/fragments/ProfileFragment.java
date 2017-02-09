package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.pager.AuthorBooksPager;
import com.ansgar.mylib.ui.presenter.fragment.ProfileFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.ProfileFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 24.1.17.
 */

public class ProfileFragment extends BaseFragment implements ProfileFragmentView {

    private static final int LAYOUT = R.layout.fragment_profile;

    private ProfileFragmentPresenter mPresenter;

    @BindView(R.id.profile_image)
    ImageView mUserAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.user_email)
    TextView mUserEmail;
    @BindView(R.id.book_count)
    TextView mBookCount;
    @BindView(R.id.author_count)
    TextView mAuthorCount;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.initializeView();
    }

    @OnClick(R.id.author_count)
    public void openAuthorFragment(){
        mPresenter.openFragment(ProfileFragmentPresenter.AUTHOR_FRAGMENT);
    }

    @OnClick(R.id.book_count)
    public void openBookFragment(){
        mPresenter.openFragment(ProfileFragmentPresenter.BOOK_FRAGMENT);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new ProfileFragmentPresenter(this);
    }

    @Override
    public void setScreenTitle(String title) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setScreenTitle(title);
    }

    @Override
    public void setUserAvatar(String avatar) {
        Picasso.with(getContext())
                .load(avatar)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .into(mUserAvatar);
    }

    @Override
    public void setBookCount(String count) {
        mBookCount.setText(count);
    }

    @Override
    public void setAuthorCount(String count) {
        mAuthorCount.setText(count);
    }

    @Override
    public void setUserName(String name) {
        mUserName.setText(name);
    }

    @Override
    public void setUserEmail(String email) {
        mUserEmail.setText(email);
    }
}
