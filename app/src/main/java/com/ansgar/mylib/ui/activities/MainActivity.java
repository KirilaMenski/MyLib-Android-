package com.ansgar.mylib.ui.activities;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseActivity;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AuthorsFragment;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.fragments.MapFragment;
import com.ansgar.mylib.ui.fragments.ProfileFragment;
import com.ansgar.mylib.ui.fragments.ReadingListFragment;
import com.ansgar.mylib.ui.fragments.SettingsFragment;
import com.ansgar.mylib.ui.presenter.activity.MainActivityPresenter;
import com.ansgar.mylib.ui.view.activity.MainActivityView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.LanguageUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.NetWorkUtils;
import com.ansgar.mylib.util.PermissionsUtil;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainActivityView, FragmentManager.OnBackStackChangedListener, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

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

    BottomNavigationView mBottomNavigationView;

    ActionBarDrawerToggle mToggle;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
//        TranslucenStatusBarUtils.setTranslucentStatusBar(getWindow(), this);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null) {
            FragmentUtil.replaceFragment(this, R.id.main_fragment_container, AuthorsFragment.newInstance(), false);
        } else {
            setFooterVis(false);
        }
        mPresenter.initializeView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LanguageUtil.setCurrentLang(getContext());
    }

    @OnClick(R.id.tv_logout)
    public void logout() {
        MyLibPreference.clearData();
        startActivity(LogInActivity.newIntent(getContext()));
        finish();
    }

    @OnClick(R.id.profile)
    public void openProfile() {
        FragmentUtil.clearBackStack((FragmentActivity) getActivity(), ((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount());
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, ProfileFragment.newInstance(), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_users)
    public void openUsers() {
//        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, MyLibraryFragment.newInstance(0), false);
        FragmentUtil.clearBackStack((FragmentActivity) getActivity(), ((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount());
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_my_lib)
    public void openMyLib() {
        FragmentUtil.clearBackStack((FragmentActivity) getActivity(), ((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount());
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, AuthorsFragment.newInstance(), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_settings)
    public void openSettings() {
        FragmentUtil.clearBackStack((FragmentActivity) getActivity(), ((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount());
        FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, SettingsFragment.newInstance(), false);
        mDrawer.closeDrawers();
    }

    @OnClick(R.id.tv_map)
    public void openMap() {
        new PermissionsUtil(this).requestPermission(new String[]{PermissionsUtil.ACCESS_FINE_LOCATION, PermissionsUtil.ACCESS_COARSE_LOCATION},
                PermissionsUtil.LOCATION_REQUEST_CODE);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionsUtil.LOCATION_REQUEST_CODE) {
            if (NetWorkUtils.isNetworkConnected(getContext())) {
                FragmentUtil.clearBackStack((FragmentActivity) getActivity(), ((FragmentActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount());
                FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, MapFragment.newInstance(), false);
                mDrawer.closeDrawers();
            } else {
                Toast.makeText(getContext(), getString(R.string.connection_failed), Toast.LENGTH_SHORT).show();
            }
        }
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
            mToolbar.setNavigationOnClickListener(this);
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
                .placeholder(R.drawable.ic_default_avatar)
                .into(mAvatar);
    }

    @Override
    public void setFooterVis(boolean vis) {
        if (mBottomNavigationView != null)
            mBottomNavigationView.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        Fragment currFragment = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        // TODO if (currFragment instanceof AddAuthorFragment || currFragment instanceof AddBookFragment)
        trans.remove(currFragment);
        trans.commit();
        manager.popBackStack();
        hideKeyBoard();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.authors:
                FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, AuthorsFragment.newInstance(), false);
                break;
            case R.id.books:
                FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, BooksFragment.newInstance(-1, true, false, true), false);
                break;
            case R.id.reading_list:
                FragmentUtil.replaceFragment((FragmentActivity) getActivity(), R.id.main_fragment_container, ReadingListFragment.newInstance(), false);
                break;
        }
        return true;
    }
}
