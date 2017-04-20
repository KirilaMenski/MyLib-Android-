package com.ansgar.mylib.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.listener.LocaleDialogListener;
import com.ansgar.mylib.util.MyLibPreference;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 2.2.17.
 */

public class LocaleDialog extends DialogFragment {

    private WeakReference<LocaleDialogListener> mListener;
    private Dialog mDialog;

    @BindView(R.id.eng)
    TextView mEng;
    @BindView(R.id.bel)
    TextView mBel;
    @BindView(R.id.rus)
    TextView mRus;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_locale, null);
        ButterKnife.bind(this, view);
        mDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        switch (MyLibPreference.getCurrentLang()){
            case MyLibPreference.ENG_LANG:
                mEng.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.ic_eng), null);
                break;
            case MyLibPreference.BEL_LANG:
                mBel.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.ic_bel), null);
                break;
            case MyLibPreference.RUS_LANG:
                mRus.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getContext(), R.drawable.ic_rus), null);
                break;
        }
    }

    @OnClick(R.id.eng)
    public void selectEng() {
        mDialog.dismiss();
        MyLibPreference.saveCurrentLang(MyLibPreference.ENG_LANG);
        mListener.get().localeSelected(MyLibPreference.ENG_LANG);
    }

    @OnClick(R.id.bel)
    public void selectBel() {
        mDialog.dismiss();
        MyLibPreference.saveCurrentLang(MyLibPreference.BEL_LANG);
        mListener.get().localeSelected(MyLibPreference.BEL_LANG);
    }

    @OnClick(R.id.rus)
    public void selectRus() {
        mDialog.dismiss();
        MyLibPreference.saveCurrentLang(MyLibPreference.RUS_LANG);
        mListener.get().localeSelected(MyLibPreference.RUS_LANG);
    }

    public void setListener(LocaleDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

}
