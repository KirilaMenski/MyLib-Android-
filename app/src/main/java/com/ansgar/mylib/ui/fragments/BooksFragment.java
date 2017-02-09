package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
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

import java.lang.ref.WeakReference;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by kirill on 24.1.17.
 */

public class BooksFragment extends BaseFragment implements BooksFragmentView, EntitySelectedListener {

    private final int LAYOUT_BOOKS = R.layout.fragment_books;
    private final int LAYOUT_AUTHOR_BOOKS = R.layout.fragment_author_books;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";
    private static final String EXTRA_SET_MENU = "com.ansgar.mylib.ui.fragments.set_menu";
    private static final String EXTRA_AUTHOR_BOOKS = "com.ansgar.mylib.ui.fragments.author_books";

    private BooksFragmentPresenter mPresenter;
    private WeakReference<BooksAdapter> mAdapter;

    private Author mAuthor;
    private boolean mLandscape;
    private boolean mAuthorBooks;
    private boolean mHasOptionMenu;

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
    @BindView(R.id.recycler_books)
    RecyclerView mBooksRecycler;
    @Nullable
    @BindView(R.id.book_citations_container_layout)
    FrameLayout mBookCitations;
    @Nullable
    @BindView(R.id.books_screen)
    LinearLayout mBooksScreen;

    public static BooksFragment newInstance(Author author, boolean setHasOptionMenu, boolean authorBooks) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        args.putBoolean(EXTRA_SET_MENU, setHasOptionMenu);
        args.putBoolean(EXTRA_AUTHOR_BOOKS, authorBooks);
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
        mAuthorBooks = getArguments().getBoolean(EXTRA_AUTHOR_BOOKS);
        View view = inflater.inflate(mAuthorBooks ? LAYOUT_AUTHOR_BOOKS : LAYOUT_BOOKS, container, false);
        ButterKnife.bind(this, view);
        mHasOptionMenu = getArguments().getBoolean(EXTRA_SET_MENU);
        mAuthor = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBookCitations != null) {
            mLandscape = true;
            showBookCitations(null);
        }
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
                AddBookFragment.newInstance(mAuthor, null), true, R.anim.right_out, R.anim.left_out);
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
            mPresenter.loadBooks(mAuthor, MyLibPreference.getBookSortType());
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
        mAdapter = new WeakReference<>(new BooksAdapter(books, getActivity(), false, mLandscape));
        mAdapter.get().setListener(this);
        mBooksRecycler.setLayoutManager((mLandscape) ? new LinearLayoutManager(getContext()) : new GridLayoutManager(getContext(), 4));
        mBooksRecycler.setAdapter(mAdapter.get());
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
        if (mBooksScreen != null) mBooksScreen.setVisibility(vis ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setSearchVisibility(boolean vis) {
        mSearchLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void authorSelected(Author author) {

    }

    @Override
    public void bookSelected(Book book) {
        showBookCitations(book);
    }

    private void showBookCitations(Book book) {
        FragmentUtil.replaceFragment(getActivity(), R.id.book_citations_container_layout, BookCitationsFragment.newInstance(book), false);
    }

}
