package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.LocaleDialog;
import com.ansgar.mylib.ui.listener.LocaleDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.SettingsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.SettingsFragmentView;
import com.ansgar.mylib.util.LanguageUtil;
import com.ansgar.mylib.util.MyLibPreference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 2.2.17.
 */

public class SettingsFragment extends BaseFragment implements SettingsFragmentView, LocaleDialogListener {

    private SettingsFragmentPresenter mPresenter;

    @BindView(R.id.lang)
    TextView mLocale;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.lang)
    public void openLocaleDialog() {
        LocaleDialog dialog = new LocaleDialog();
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "localeDialog");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMainActivity().setScreenTitle(getString(R.string.settings));
    }

    @Override
    public void onStart() {
        super.onStart();
        setLocaleIcon(MyLibPreference.getCurrentLang());
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SettingsFragmentPresenter(this);
    }

    @Override
    public void localeSelected(String locale) {
        switch (locale){
            case MyLibPreference.ENG_LANG:
                LanguageUtil.setLanguage(getContext(), LanguageUtil.ENG);
                break;
            case MyLibPreference.BEL_LANG:
                LanguageUtil.setLanguage(getContext(), LanguageUtil.BEL);
                break;
            case MyLibPreference.RUS_LANG:
                LanguageUtil.setLanguage(getContext(), LanguageUtil.RUS);
                break;
        }
        getActivity().recreate();
        mLocale.setText(locale);//TODO
    }

    private void setLocaleIcon(String locale){
        switch (locale){
            case MyLibPreference.ENG_LANG:
                mLocale.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_eng), null);
                break;
            case MyLibPreference.BEL_LANG:
                mLocale.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_bel), null);
                break;
            case MyLibPreference.RUS_LANG:
                mLocale.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,
                        ContextCompat.getDrawable(getContext(), R.drawable.ic_rus), null);
                break;
        }
    }

}
