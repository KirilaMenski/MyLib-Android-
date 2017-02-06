package com.ansgar.mylib.ui.pager;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AuthorDetailsFragment;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.presenter.fragment.AuthorBooksPagerPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksPagerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 30.1.17.
 */

public class AuthorBooksPager extends BaseFragment implements AuthorBooksPagerView {

    private static final int LAYOUT = R.layout.pager_author_books;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.pager.author";

    private AuthorBooksPagerPresenter mPresenter;
    private Author mAuthor;

    private int mPosition;

    @BindView(R.id.author_books_pager)
    ViewPager mAuthorBooksPager;

    public static AuthorBooksPager newInstance(Author author) {
        AuthorBooksPager pager = new AuthorBooksPager();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        pager.setArguments(args);
        return pager;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        mAuthor = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuthorBooksPager.setAdapter(getAdapter());
        mAuthorBooksPager.addOnPageChangeListener(getPageListener());
        mAuthorBooksPager.setCurrentItem(mPosition);
    }

    private ViewPager.OnPageChangeListener getPageListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    private FragmentStatePagerAdapter getAdapter() {
        return new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) return AuthorDetailsFragment.newInstance(mAuthor);
                if (position == 1) return BooksFragment.newInstance(mAuthor, true, false);
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
