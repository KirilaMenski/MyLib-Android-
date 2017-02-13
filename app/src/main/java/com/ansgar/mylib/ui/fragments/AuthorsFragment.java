package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.AuthorsAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SortDialog;
import com.ansgar.mylib.ui.listener.EntitySelectedListener;
import com.ansgar.mylib.ui.presenter.fragment.AuthorsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

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

public class AuthorsFragment extends BaseFragment implements AuthorsFragmentView, EntitySelectedListener {

    private static final int LAYOUT = R.layout.fragment_authors;

    private AuthorsFragmentPresenter mPresenter;
    private AuthorsAdapter mAdapter;
    private boolean mLandscape;

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
    @BindView(R.id.recycler_select_object)
    RecyclerView mAuthorsRecycler;
    @Nullable
    @BindView(R.id.author_book_container_layout)
    FrameLayout mSecondFrameLayout;
    @Nullable
    @BindView(R.id.authors_screen)
    LinearLayout mAuthorsScreen;

    public static AuthorsFragment newInstance() {
        AuthorsFragment authorsFragment = new AuthorsFragment();
        return authorsFragment;
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
        if (mSecondFrameLayout != null) {
            mLandscape = true;
            mPresenter.replaceFragment();
        }
        mPresenter.loadAuthors(MyLibPreference.getAuthorSortType());

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
                addAuthor();
                break;
            case R.id.sort:
                SortDialog dialog = SortDialog.newInstance(MyLibPreference.SORT_TYPE_AUTHOR);
                dialog.setListener(mPresenter);
                dialog.show(getFragmentManager(), "sortTypeAuthorDialog");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataType.setText(getContext().getResources().getString(R.string.add_data,
                getContext().getResources().getString(R.string.author).toLowerCase()));
    }

    @OnClick(R.id.add_data)
    public void addAuthor() {
        FragmentUtil.replaceAnimFragment(getActivity(),
                R.id.main_fragment_container, AddAuthorFragment.newInstance(null),
                true, R.anim.right_out, R.anim.left_out);
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
            mPresenter.loadAuthors(MyLibPreference.getAuthorSortType());
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AuthorsFragmentPresenter(this);
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
        if (mSecondFrameLayout != null)
            mSecondFrameLayout.setVisibility(vis ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setSearchVisibility(boolean vis) {
        mSearchLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setProgressBarVis(boolean vis) {
        mProgressBar.setVisibility(vis ? View.VISIBLE : View.GONE);
        if (mSecondFrameLayout != null)
            mSecondFrameLayout.setVisibility(vis ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setAuthorAdapter(List<Author> authors) {
        mAdapter = new AuthorsAdapter(authors, getActivity(), false, mLandscape);
        mAdapter.setListener(this);
        mAuthorsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuthorsRecycler.setAdapter(mAdapter);
        mAuthorsRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void authorSelected(int authorId, String firstName, String lastName) {
        mPresenter.selectAuthorBooks(authorId);
    }

    @Override
    public void bookSelected(Book book) {

    }
}
