package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.ui.adapter.CitationAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.BookDetailsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.BookDetailsFragmentView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 30.1.17.
 */

public class BookDetailsFragment extends BaseFragment implements BookDetailsFragmentView {

    private static final int LAYOUT = R.layout.fragment_book_details;
    private static final String EXTRA_BOOK = "com.ansgar.mylib.ui.fragments.book";

    private BookDetailsFragmentPresenter mPresenter;
    private CitationAdapter mAdapter;
    private boolean mCitEditVis;

    @BindView(R.id.book_cover)
    ImageView mBookCover;
    @BindView(R.id.author_name)
    TextView mAuthorName;
    @BindView(R.id.book_title)
    TextView mBookTitle;
    @BindView(R.id.book_date)
    TextView mBookDate;
    @BindView(R.id.book_genre)
    TextView mBookGenre;
    @BindView(R.id.book_series)
    TextView mBookSeries;
    @BindView(R.id.description)
    TextView mBookDescription;
    @BindView(R.id.add_citation)
    TextView mAddCitation;
    @BindView(R.id.citation)
    EditText mNewCitation;
    @BindView(R.id.recycler_citation)
    RecyclerView mCitationRec;

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        ButterKnife.bind(this, view);
        Book book = (Book) getArguments().getSerializable(EXTRA_BOOK);
        mPresenter.initializeView(book);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.loadCitation();
    }

    @OnClick(R.id.add_citation)
    public void addCitation(){
        if (mCitEditVis){
            mNewCitation.setVisibility(View.GONE);
            mNewCitation.setText("");
            mCitEditVis = false;
            mPresenter.addNewCitation(mNewCitation.getText().toString());
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
        mPresenter = new BookDetailsFragmentPresenter(this);
    }

    @Override
    public void setBookCover(String cover) {
        Picasso.with(getContext())
                .load(cover)
                .fit()
                .centerCrop()
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_synchronize))
                .into(mBookCover);
    }

    @Override
    public void setAuthorName(String authorName) {
        mAuthorName.setText(authorName);
    }

    @Override
    public void setBookTitle(String title) {
        mBookTitle.setText(title);
    }

    @Override
    public void setBookDate(String date) {
        mBookDate.setText(date);
    }

    @Override
    public void setBookGenre(String genre) {
        mBookGenre.setText(genre);
    }

    @Override
    public void setSeries(String series) {
        mBookSeries.setText(series);
    }

    @Override
    public void setDescription(String description) {
        mBookDescription.setText(description);
    }

    @Override
    public void setAdapter(List<Citation> citations) {
        mAdapter = new CitationAdapter(citations, getActivity(), mPresenter);
        mCitationRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mCitationRec.setAdapter(mAdapter);
        mCitationRec.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void notifyAdapter() {
        mCitationRec.getAdapter().notifyDataSetChanged();
    }
}
