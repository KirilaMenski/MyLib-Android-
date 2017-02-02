package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.BooksAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SortDialog;
import com.ansgar.mylib.ui.presenter.fragment.BooksFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.BooksFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.kevin.loopview.AdLoopView;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

public class BooksFragment extends BaseFragment implements BooksFragmentView {

    private final int LAYOUT = R.layout.fragment_books;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";

    private BooksFragmentPresenter mPresenter;
    private BooksAdapter mAdapter;

    private Author mAuthor;

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
    RelativeLayout mNoItemLayout;
    @BindView(R.id.recycler_books)
    RecyclerView mBooksRecycler;

    public static BooksFragment newInstance(Author author) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        fragment.setArguments(args);
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
        mAuthor = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadBooks(mAuthor, MyLibPreference.getBookSortType());
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
                addBook();
                break;
            case R.id.sort:
                SortDialog dialog = SortDialog.newInstance(MyLibPreference.SORT_TYPE_BOOK);
                dialog.setListener(mPresenter);
                dialog.show(getFragmentManager(), "sortTypeBookDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataType.setText(getContext().getResources().getString(R.string.add_data,
                getContext().getResources().getString(R.string.book).toLowerCase()));
    }

    @OnClick(R.id.add_data)
    public void addBook() {
        FragmentUtil.replaceAnimFragment(getActivity(), R.id.main_fragment_container,
                AddBookFragment.newInstance(null, null), true, R.anim.right_out, R.anim.left_out);
    }

    @OnClick(R.id.cancel)
    public void cancelSearch() {
        setSearchVisibility(false);
        hideKeyBoard();
    }

    @OnTextChanged(R.id.search)
    public void onTextChanged() {
        if (mAdapter != null) mAdapter.getFilter().filter(mSearchEt.getText().toString());
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new BooksFragmentPresenter(this);
    }

    @Override
    public void setAdapter(List<Book> books) {
        mAdapter = new BooksAdapter(books, getActivity(), false);
        mBooksRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mBooksRecycler.setAdapter(mAdapter);
        mBooksRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setSearchVisibility(boolean vis) {
        mSearchLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }
}
