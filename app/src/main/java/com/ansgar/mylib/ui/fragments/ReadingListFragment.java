package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.ReadingListAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SelectEntityDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedDialogListener;
import com.ansgar.mylib.ui.presenter.fragment.ReadingListFragmentPresenter;
import com.ansgar.mylib.ui.presenter.fragment.SelectEntityDialogPresenter;
import com.ansgar.mylib.ui.view.fragment.ReadingListFragmentView;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by kirill on 24.1.17.
 */

public class ReadingListFragment extends BaseFragment implements ReadingListFragmentView, EntitySelectedDialogListener {

    private static final int LAYOUT = R.layout.fragment_reading_list;

    private ReadingListFragmentPresenter mPresenter;
    private WeakReference<ReadingListAdapter> mAdapter;

    @BindView(R.id.progress_bar_layout)
    LinearLayout mProgressBar;
    @BindView(R.id.ll_search)
    LinearLayout mSearchLayout;
    @BindView(R.id.search)
    EditText mSearchEt;
    @BindView(R.id.cancel)
    TextView mCancelSearch;
    @BindView(R.id.type)
    TextView mDataType;
    @BindView(R.id.add_data)
    LinearLayout mAddAuthor;
    @BindView(R.id.layout_no_item)
    LinearLayout mNoItemLayout;
    @BindView(R.id.recycler_reading_list)
    RecyclerView mReadingListRec;

    public static ReadingListFragment newInstance() {
        ReadingListFragment fragment = new ReadingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadList(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                setSearchVisibility(true);
                break;
            case R.id.add:
                addBookToList();
                break;
            case R.id.sort:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_data)
    public void addBookToList() {
        SelectEntityDialog dialog = SelectEntityDialog.newInstance(SelectEntityDialogPresenter.BOOKS);
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "selectBooksDialog");
    }

    @OnClick(R.id.cancel)
    public void cancelSearch() {
        setSearchVisibility(false);
        hideKeyBoard();
    }

    @OnTextChanged(R.id.search)
    public void onTextChanged() {
        if (mSearchEt.length() > 0) {
            mAdapter.get().getFilter().filter(mSearchEt.getText().toString());
        } else {
            mPresenter.loadList(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataType.setText(getContext().getResources().getString(R.string.add_data,
                getContext().getResources().getString(R.string.book).toLowerCase()));
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new ReadingListFragmentPresenter(this);
    }

    @Override
    public void setAdapter(List<Book> books) {
        mAdapter = new WeakReference<>(new ReadingListAdapter(books, getActivity(), mPresenter));
        mReadingListRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mReadingListRec.setAdapter(mAdapter.get());
    }

    @Override
    public void notifyData() {
        mReadingListRec.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setSearchVisibility(boolean vis) {
        mSearchLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setProgressBarVis(boolean vis) {
        mProgressBar.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void authorSelected(int authorId, String firstName, String lastName) {

    }

    @Override
    public void bookSelected(Book book) {
        mPresenter.addBookToList(book);
    }
}
