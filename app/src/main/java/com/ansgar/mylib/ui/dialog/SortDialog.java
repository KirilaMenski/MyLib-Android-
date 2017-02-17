package com.ansgar.mylib.ui.dialog;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.adapter.SortTypeAdapter;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.SortDialogListener;
import com.ansgar.mylib.ui.listener.SortTypeAdapterListener;
import com.ansgar.mylib.ui.presenter.dialog.SortDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.SortDialogView;

import java.lang.ref.WeakReference;

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
 * Created by kirill on 1.2.17.
 */

public class SortDialog extends BaseDialog implements SortDialogView, SortTypeAdapterListener{

    private static final int LAYOUT = R.layout.dialog_sort;
    private static final String EXTRA_TYPE = "com.ansgar.mylib.ui.dialog.type";

    private SortDialogPresenter mPresenter;
    private Dialog mDialog;
    private SortTypeAdapter mAdapter;
    private WeakReference<SortDialogListener> mListener;

    private String mType;

    @BindView(R.id.recycler_sort)
    RecyclerView mSortTypesRec;

    public static SortDialog newInstance(String type) {
        SortDialog dialog = new SortDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
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
        setAdapter();
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SortDialogPresenter(this);
    }

    @Override
    public void setAdapter() {
        mAdapter = new SortTypeAdapter(mType, this, getActivity());
        mSortTypesRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mSortTypesRec.setAdapter(mAdapter);
    }

    @Override
    public void selectedSortType(String type, int position) {
        mDialog.dismiss();
        mListener.get().sortTypePosition(position);
    }

    public void setListener(SortDialogListener listener){
        mListener = new WeakReference<>(listener);
    }

}
