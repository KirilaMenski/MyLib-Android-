package com.ansgar.mylib.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.AddAuthorDialogListener;
import com.ansgar.mylib.ui.presenter.dialog.AddAuthorDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.AddAuthorDialogView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddAuthorDialog extends BaseDialog implements AddAuthorDialogView {

    private static final int LAYOUT = R.layout.dialog_add_author;

    private AddAuthorDialogPresenter mPresenter;
    private Dialog mDialog;
    private WeakReference<AddAuthorDialogListener> mListener;

    private String mAuthorIconPath;

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
    @BindView(R.id.add_author)
    TextView mAdd;

    public static AddAuthorDialog newInstance() {
        AddAuthorDialog fragment = new AddAuthorDialog();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
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
        return mDialog;
    }

    @OnClick(R.id.add_author)
    public void addAuthor() {
        mPresenter.addAuthor(mListener.get(), mAuthorIconPath, mFisrtName.getText().toString(),
                mLastName.getText().toString(), mDate.getText().toString(), mBiography.getText().toString());
        mDialog.dismiss();
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AddAuthorDialogPresenter(this);
    }

    public void setListener(AddAuthorDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

}
