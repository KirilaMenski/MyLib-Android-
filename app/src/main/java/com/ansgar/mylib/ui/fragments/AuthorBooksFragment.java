package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.adapter.BooksAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.AuthorBooksFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksFragmentView;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirila on 30.1.17.
 */
public class AuthorBooksFragment extends BaseFragment implements AuthorBooksFragmentView {

    private static final int LAYOUT = R.layout.fragment_author_books;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";

    private AuthorBooksFragmentPresenter mPresenter;
    private BooksAdapter mBooksAdapter;

    @BindView(R.id.add_book)
    TextView mAddBook;
    @BindView(R.id.recycler_books)
    RecyclerView mAuthorBooksRec;

    public static AuthorBooksFragment newInstance(Author author) {
        AuthorBooksFragment fragment = new AuthorBooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        Author author = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        mPresenter.initializeViews(author);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadAuthorBooks();
    }

    @OnClick(R.id.add_book)
    public void addCitation(){
        mPresenter.openAddBookFragment();
    }

    @Override
    public void setAuthorBooksAdapter(List<Book> books) {
        mBooksAdapter = new BooksAdapter(books, getActivity(), false);
        mAuthorBooksRec.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAuthorBooksRec.setAdapter(mBooksAdapter);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AuthorBooksFragmentPresenter(this);
    }
}
