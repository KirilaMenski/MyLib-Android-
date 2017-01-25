package com.ansgar.mylib.ui.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.AddAuthorDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.AddAuthorFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 25.1.17.
 */

public class AddAuthorFragment extends BaseFragment implements AddAuthorFragmentView {

    private static final int LAYOUT = R.layout.fragment_add_author;

    private AddAuthorFragmentPresenter mPresenter;

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

    public static AddAuthorFragment newInstance() {
        AddAuthorFragment fragment = new AddAuthorFragment();
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.add_author)
    public void addAuthor() {
        mPresenter.addAuthor(mAuthorIconPath, mFisrtName.getText().toString(),
                mLastName.getText().toString(), mDate.getText().toString(), mBiography.getText().toString());
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AddAuthorFragmentPresenter(this);
    }

}
