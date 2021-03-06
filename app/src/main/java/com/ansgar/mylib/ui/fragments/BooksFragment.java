package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.BooksAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SortDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.presenter.fragment.BooksFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.BooksFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by kirill on 24.1.17.
 */

public class BooksFragment extends BaseFragment implements BooksFragmentView, EntitySelectedListener {

    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";
    private static final String EXTRA_SET_MENU = "com.ansgar.mylib.ui.fragments.set_menu";
    private static final String EXTRA_AUTHOR_BOOKS = "com.ansgar.mylib.ui.fragments.author_books";
    private static final String EXTRA_SHOW_FOOTER = "com.ansgar.mylib.ui.fragments.show_footer";

    private BooksFragmentPresenter mPresenter;
    private BooksAdapter mAdapter;

    private long mAuthorId;
    private boolean mLandscape;
    private boolean mShowFooter;

    @BindView(R.id.progress_bar_layout)
    ProgressBar mProgressBar;
    @BindView(R.id.ll_search)
    LinearLayout mSearchLayout;
    @BindView(R.id.search)
    EditText mSearchEt;
    @BindView(R.id.type)
    TextView mDataType;
    @BindView(R.id.layout_no_item)
    LinearLayout mNoItemLayout;
    @BindView(R.id.recycler_books)
    RecyclerView mBooksRecycler;
    @Nullable
    @BindView(R.id.book_citations_container_layout)
    FrameLayout mBookCitations;
    @Nullable
    @BindView(R.id.books_screen)
    LinearLayout mBooksScreen;

    public static BooksFragment newInstance(long authorId, boolean setHasOptionMenu, boolean authorBooks, boolean showFooter) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_AUTHOR, authorId);
        args.putBoolean(EXTRA_SET_MENU, setHasOptionMenu);
        args.putBoolean(EXTRA_AUTHOR_BOOKS, authorBooks);
        args.putBoolean(EXTRA_SHOW_FOOTER, showFooter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(getArguments().getBoolean(EXTRA_SET_MENU));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boolean authorBooks = getArguments().getBoolean(EXTRA_AUTHOR_BOOKS);
        View view = inflater.inflate(authorBooks ? R.layout.fragment_author_books : R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);
        mAuthorId = getArguments().getLong(EXTRA_AUTHOR);
        mShowFooter = getArguments().getBoolean(EXTRA_SHOW_FOOTER);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMainActivity().setFooterVis(mShowFooter);
        if (mBookCitations != null) {
            mLandscape = true;
            showBookCitations(-1);
        }
        mPresenter.loadBooks(mAuthorId, MyLibPreference.getBookSortType());
    }

    @Override
    public void onPause() {
        getMainActivity().setFooterVis(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        MyLibPreference.removeBookPos();
        super.onDestroy();
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
        if (mShowFooter) getMainActivity().setScreenTitle(getContext().getString(R.string.books));
    }

    @OnClick(R.id.add_data)
    public void addBook() {
        FragmentUtil.replaceAnimFragment(getActivity(), R.id.main_fragment_container,
                AddBookFragment.newInstance(mAuthorId, -1), true, R.anim.right_out, R.anim.left_out);
    }

    @OnClick(R.id.cancel)
    public void cancelSearch() {
        setSearchVisibility(false);
        hideKeyBoard();
    }

    @OnTextChanged(R.id.search)
    public void onTextChanged() {
        if (mSearchEt.length() > 0) {
            mAdapter.getFilter().filter(mSearchEt.getText().toString());
        } else {
            mPresenter.loadBooks(mAuthorId, MyLibPreference.getBookSortType());
        }
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
        mAdapter = new BooksAdapter(books, getActivity(), false, mLandscape);
        mAdapter.setListener(this);
        mBooksRecycler.setLayoutManager((mLandscape) ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 4));
        mBooksRecycler.setAdapter(mAdapter);
        mBooksRecycler.scrollToPosition(MyLibPreference.getBookPos());
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        if (mNoItemLayout != null) mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
        if (mBooksScreen != null) mBooksScreen.setVisibility(vis ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setSearchVisibility(boolean vis) {
        mSearchLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setProgressBarVis(boolean vis) {
        mProgressBar.setVisibility(vis ? View.VISIBLE : View.GONE);
        if (mBooksScreen != null) mBooksScreen.setVisibility(vis ? View.GONE : View.VISIBLE);
    }

    @Override
    public void authorSelected(long authorId, String firstName, String lastName) {

    }

    @Override
    public void bookSelected(long bookId) {
        showBookCitations(bookId);
    }

    private void showBookCitations(long bookId) {
        FragmentUtil.replaceFragment(getActivity(), R.id.book_citations_container_layout, BookCitationsFragment.newInstance(bookId), false);
    }

}
