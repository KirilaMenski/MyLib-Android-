package com.ansgar.mylib.ui.dialog;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.adapter.SelectAdapter;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.SelectAdapterListener;
import com.ansgar.mylib.ui.listener.SelectDialogListener;
import com.ansgar.mylib.ui.presenter.dialog.SelectDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.SelectDialogView;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 26.1.17.
 */

public class SelectDialog extends BaseDialog implements SelectDialogView, SelectAdapterListener {

    public static final String GENRE_TYPE = "genre_value";
    public static final String INT_TYPE = "int_value";
    private static final String EXTRA_TYPE = "com.ansgar.mylib.ui.dialog.type";

    private WeakReference<SelectDialogListener> mListener;
    private SelectDialogPresenter mPresenter;
    private SelectAdapter mAdapter;
    private Dialog mDialog;
    private String mType;

    @BindView(R.id.recycler_value)
    RecyclerView mValueRec;

    public static final SelectDialog newInstance(String valueType) {
        SelectDialog dialog = new SelectDialog();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TYPE, valueType);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select, null);
        ButterKnife.bind(this, view);
        mType = getArguments().getString(EXTRA_TYPE);
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
        mPresenter.loadValue(mType);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SelectDialogPresenter(this);
    }

    @Override
    public void setAdapter(List<String> value) {
        mAdapter = new SelectAdapter(value, getActivity(), this, mType);
        mValueRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mValueRec.setAdapter(mAdapter);
    }

    public void setListener(SelectDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public void itemSelected(String value, boolean other) {
        mListener.get().itemSelected(value, other);
        mDialog.dismiss();
    }
}
