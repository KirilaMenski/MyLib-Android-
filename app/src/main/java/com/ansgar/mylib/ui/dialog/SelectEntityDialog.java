package com.ansgar.mylib.ui.dialog;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.AuthorsAdapter;
import com.ansgar.mylib.ui.adapter.BooksAdapter;
import com.ansgar.mylib.ui.base.BaseDialog;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.EntitySelectedDialogListener;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.presenter.fragment.SelectEntityDialogPresenter;
import com.ansgar.mylib.ui.view.dialog.SelectEntityDialogView;

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
 * Created by kirill on 25.1.17.
 */

public class SelectEntityDialog extends BaseDialog implements SelectEntityDialogView, EntitySelectedListener {

    private static final int LAYOUT = R.layout.dialog_select_object;
    private static final String EXTRA_ENTITY = "com.ansgar.mylib.ui.dialog.entity";

    private SelectEntityDialogPresenter mPresenter;
    private Dialog mDialog;
    private AuthorsAdapter mAuthorsAdapter;
    private WeakReference<BooksAdapter> mBooksAdapter;
    private WeakReference<EntitySelectedDialogListener> mListener;

    private String mEntityType;

    @BindView(R.id.recycler_select_object)
    RecyclerView mEntityRecycler;

    public static SelectEntityDialog newInstance(String entityType) {
        SelectEntityDialog dialog = new SelectEntityDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_ENTITY, entityType);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        ButterKnife.bind(this, view);
        mEntityType = getArguments().getString(EXTRA_ENTITY);
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
        mPresenter.loadEntity(mEntityType);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new SelectEntityDialogPresenter(this);
    }

    @Override
    public void setAuthorsAdapter(List<Author> authors) {
        mAuthorsAdapter = new AuthorsAdapter(authors, getActivity(), true, false);
        mAuthorsAdapter.setListener(this);
        mEntityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mEntityRecycler.setAdapter(mAuthorsAdapter);
    }

    @Override
    public void setBooksAdapter(List<Book> books) {
        mBooksAdapter = new WeakReference<>(new BooksAdapter(books, getActivity(), true, false));
        mBooksAdapter.get().setListener(this);
        mEntityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mEntityRecycler.setAdapter(mBooksAdapter.get());
    }

    public void setListener(EntitySelectedDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public void authorSelected(long authorId, String firstName, String lastName) {
        mListener.get().authorSelected(authorId, firstName, lastName);
        mDialog.dismiss();
    }

    @Override
    public void bookSelected(long bookId) {
        mListener.get().bookSelected(bookId);
        mDialog.dismiss();
    }
}
