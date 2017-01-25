package com.ansgar.mylib.ui.dialog;

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

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.adapter.AuthorsAdapter;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.AuthorSelectedDialogListener;
import com.ansgar.mylib.ui.listener.AuthorSelectedListener;
import com.ansgar.mylib.ui.presenter.fragment.SelectAuthorDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.SelectAuthorDialogView;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 25.1.17.
 */

public class SelectAuthorDialog extends BaseDialog implements SelectAuthorDialogView, AuthorSelectedListener {

    private static final int LAYOUT = R.layout.dialog_select_author;

    private SelectAuthorDialogPresenter mPresenter;
    private Dialog mDialog;
    private AuthorsAdapter mAdapter;
    private WeakReference<AuthorSelectedDialogListener> mListener;

    @BindView(R.id.recycler_authors)
    RecyclerView mAuthorRecycler;

    public static SelectAuthorDialog newInstance() {
        SelectAuthorDialog dialog = new SelectAuthorDialog();
        return dialog;
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
        mDialog.setCancelable(false);
        return mDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadAuthors();
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SelectAuthorDialogPresenter(this);
    }

    @Override
    public void setAdapter(List<Author> authors) {
        mAdapter = new AuthorsAdapter(authors, getActivity(), true);
        mAdapter.setListener(this);
        mAuthorRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuthorRecycler.setAdapter(mAdapter);
    }

    public void setListener(AuthorSelectedDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public void authorSelected(Author author) {
        mListener.get().authorSelected(author);
        mDialog.dismiss();
    }
}
