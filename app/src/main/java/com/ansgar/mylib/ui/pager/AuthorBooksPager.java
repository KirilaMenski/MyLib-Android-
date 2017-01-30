package com.ansgar.mylib.ui.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.AuthorBooksPagerPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksPagerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.1.17.
 */

public class AuthorBooksPager extends BaseFragment implements AuthorBooksPagerView {

    private static final int LAYOUT = R.layout.pager_author_books;

    private AuthorBooksPagerPresenter mPresenter;

    @BindView(R.id.author_books_pager)
    ViewPager mAuthorBooksPager;

    public AuthorBooksPager newInstance() {
        AuthorBooksPager pager = new AuthorBooksPager();
        return pager;
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
        mAuthorBooksPager.setAdapter(getAdapter());
    }

    private FragmentStatePagerAdapter getAdapter() {
        return new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AuthorBooksPagerPresenter(this);
    }
}
