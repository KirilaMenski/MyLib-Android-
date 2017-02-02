package com.ansgar.mylib.ui.activities;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.ProfileFragment;
import com.ansgar.mylib.ui.fragments.SettingsFragment;
import com.ansgar.mylib.ui.pager.MyLibraryFragment;
import com.ansgar.mylib.ui.presenter.activity.MainActivityPresenter;
import com.ansgar.mylib.ui.view.activity.MainActivityView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityView, FragmentManager.OnBackStackChangedListener {

    private static final int LAYOUT = R.layout.activity_main;
    private final String CHANGED_SETTING = "CHANGED_SETTING";

    private MainActivityPresenter mPresenter;

    @BindView(R.id.profile_image)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.tv_screen_title)
    TextView mScreenTitle;
    @BindView(R.id.profile)
    RelativeLayout mProfileRl;
    @BindView(R.id.main_fragment_container)
    FrameLayout mFrameLayout;

    ActionBarDrawerToggle mToggle;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
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
        if (savedInstanceState == null) {
            FragmentUtil.replaceFragment(this, R.id.main_fragment_container, MyLibraryFragment.newInstance(0), false);
        }
        mPresenter.initializeView();
    }

    @OnClick(R.id.tv_logout)
    public void logout() {
        MyLibPreference.clearData();
        startActivity(LogInActivity.newIntent(getContext()));
        finish();
    }

    @OnClick(R.id.profile)
    public void openProfile() {
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, ProfileFragment.newInstance(), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_my_lib)
    public void openMyLib() {
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, MyLibraryFragment.newInstance(0), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_users)
    public void openUsers() {
//        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, MyLibraryFragment.newInstance(0), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_settings)
    public void openSettings() {
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, SettingsFragment.newInstance(), false);
        mDrawer.closeDrawers();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CHANGED_SETTING, true);
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
                    hideKeyBoard();
                }
            });
        }
    }

    public void setScreenTitle(String title) {
        mScreenTitle.setText(title);
    }

    @Override
    public void setUserName(String name) {
        mUserName.setText(name);
    }

    @Override
    public void setUserAvatar(String avatar) {
        Picasso.with(this)
                .load(avatar)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_images_200dp)
                .into(mAvatar);
    }
}
