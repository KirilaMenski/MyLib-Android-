package com.ansgar.mylib.ui.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AuthorsFragment;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.fragments.ReadingListFragment;
import com.ansgar.mylib.ui.presenter.fragment.MyLibraryFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.MyLibraryFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class MyLibraryFragment extends BaseFragment implements MyLibraryFragmentView {

    private static final int LAYOUT = R.layout.pager_my_library;

    private MyLibraryFragmentPresenter mPresenter;

    private int mPosition;

    @BindView(R.id.my_lib_pager)
    ViewPager mMyLibPager;

    public static MyLibraryFragment newInstance() {
        MyLibraryFragment pager = new MyLibraryFragment();
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
        mMyLibPager.setAdapter(getAdapter());
        mMyLibPager.addOnPageChangeListener(getPageChangeListener());
        mMyLibPager.setCurrentItem(mPosition);
    }

    private FragmentStatePagerAdapter getAdapter() {
        return new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return AuthorsFragment.newInstance();
                }
                if (position == 1) {
                    return BooksFragment.newInstance(null);
                }
                if (position == 2) {
                    return ReadingListFragment.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }


    private ViewPager.OnPageChangeListener getPageChangeListener() {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setScreenTitle(getResources().getString(R.string.my_lib));
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new MyLibraryFragmentPresenter(this);
    }

}
