package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.ui.adapter.CitationAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.BookCitationsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.BookCitationsFragmentView;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
 * Created by kirill on 30.1.17.
 */
public class BookCitationsFragment extends BaseFragment implements BookCitationsFragmentView {

    private static final int LAYOUT = R.layout.fragment_book_citations;
    private static final String EXTRA_BOOK = "com.ansgar.mylib.ui.fragments.book";

    private BookCitationsFragmentPresenter mPresenter;
    private CitationAdapter mAdapter;

    private boolean mCitEditVis;

    @BindView(R.id.add_citation)
    TextView mAddCitation;
    @BindView(R.id.citation)
    EditText mNewCitation;
    @BindView(R.id.recycler_citation)
    RecyclerView mCitationRec;

    public static BookCitationsFragment newInstance(Long bookId) {
        BookCitationsFragment fragment = new BookCitationsFragment();
        Bundle args = new Bundle();
        args.putLong(EXTRA_BOOK, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        long bookId = getArguments().getLong(EXTRA_BOOK);
        mPresenter.initializeView(bookId);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadCitation();
    }

    @OnClick(R.id.add_citation)
    public void addCitation() {
        if (mCitEditVis) {
            if (mNewCitation.getText().toString().length() == 0) {
                Toast.makeText(getContext(), getString(R.string.empty_value), Toast.LENGTH_SHORT).show();
                return;
            }
            mNewCitation.setVisibility(View.GONE);
            mCitEditVis = false;
            mPresenter.addNewCitation(mNewCitation.getText().toString());
            mNewCitation.setText("");
            hideKeyBoard();
        } else {
            mNewCitation.setVisibility(View.VISIBLE);
            mCitEditVis = true;
        }
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new BookCitationsFragmentPresenter(this);
    }

    @Override
    public void setAdapter(List<Citation> citations) {
        mAdapter = new CitationAdapter(citations, getActivity(), mPresenter);
        mCitationRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mCitationRec.setAdapter(mAdapter);
        mCitationRec.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyData() {
        mCitationRec.getAdapter().notifyDataSetChanged();
    }

}
