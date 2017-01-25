package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.adapter.ReadingListAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.ReadingListFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.ReadingListFragmentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 24.1.17.
 */

public class ReadingListFragment extends BaseFragment implements ReadingListFragmentView {

    private static final int LAYOUT = R.layout.fragment_reading_list;

    private ReadingListFragmentPresenter mPresenter;
    private ReadingListAdapter mAdapter;

    @BindView(R.id.type)
    TextView mDataType;
    @BindView(R.id.recycler_reading_list)
    RecyclerView mReadingListRec;
    @BindView(R.id.layout_no_item)
    RelativeLayout mNoItemLayout;

    public static ReadingListFragment newInstance() {
        ReadingListFragment fragment = new ReadingListFragment();
        return fragment;
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
        mAdapter = new ReadingListAdapter(books, getActivity());
        mReadingListRec.setLayoutManager(new LinearLayoutManager(getContext()));
        mReadingListRec.setAdapter(mAdapter);
    }

    @Override
    public void setLayoutVisibility(boolean vis) {
        mNoItemLayout.setVisibility(vis ? View.VISIBLE : View.GONE);
    }
}
