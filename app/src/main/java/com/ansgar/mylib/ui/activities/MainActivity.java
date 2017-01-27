package com.ansgar.mylib.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.pager.MyLibraryFragment;
import com.ansgar.mylib.ui.presenter.activity.MainActivityPresenter;
import com.ansgar.mylib.ui.view.activity.MainActivityView;
import com.ansgar.mylib.util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainActivityView, FragmentManager.OnBackStackChangedListener {

    private static final int LAYOUT = R.layout.activity_main;
    public static final String EXTRA_USER = "com.ansgar.mylib.ui.activities.user_id";

    private MainActivityPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.tv_screen_title)
    TextView mScreenTitle;

    @BindView(R.id.main_fragment_container)
    FrameLayout mFrameLayout;

    ActionBarDrawerToggle mToggle;

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_USER, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
//        TranslucenStatusBarUtils.setTranslucentStatusBar(getWindow(), this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        FragmentUtil.replaceFragment(this, R.id.main_fragment_container, MyLibraryFragment.newInstance(), false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new MainActivityPresenter(this);
    }

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            mToolbar.setNavigationOnClickListener(null);
            mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            mDrawer.addDrawerListener(mToggle);
            mToggle.syncState();
            mToggle.setDrawerIndicatorEnabled(true);
        } else {
            mToggle.setDrawerIndicatorEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    Fragment currFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
                    trans.remove(currFragment);
                    trans.commit();
                    manager.popBackStack();
                }
            });
        }
    }

    public void setScreenTitle(String title) {
        mScreenTitle.setText(title);
    }
}
