package com.ansgar.mylib.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.adapter.AuthorsAdapter;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.AuthorsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 24.1.17.
 */

public class AuthorsFragment extends BaseFragment implements AuthorsFragmentView {

    private static final int LAYOUT = R.layout.fragment_author;

    private AuthorsFragmentPresenter mPresenter;
    private AuthorsAdapter mAdapter;

    @BindView(R.id.type)
    TextView mDataType;
    @BindView(R.id.add_data)
    LinearLayout mAddAuthor;
    @BindView(R.id.layout_no_item)
    RelativeLayout mNoItemLayout;
    @BindView(R.id.recycler_select_object)
    RecyclerView mAuthorsRecycler;
    @BindView(R.id.add_author_img)
    ImageView mAddAuthorImg;

    public static AuthorsFragment newInstance() {
        AuthorsFragment authorsFragment = new AuthorsFragment();
        return authorsFragment;
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
        mPresenter.loadAuthors();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDataType.setText(getContext().getResources().getString(R.string.add_data,
                getContext().getResources().getString(R.string.author).toLowerCase()));
    }

    @OnClick({R.id.add_data, R.id.add_author_img})
    public void addAuthorPressed() {
        FragmentUtil.replaceAnimFragment(getActivity(), R.id.main_fragment_container, AddAuthorFragment.newInstance(), true, R.anim.right_out, R.anim.left_out);
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
    }

    @Override
    public void setAuthorAdapter(List<Author> authors) {
        mAdapter = new AuthorsAdapter(authors, getActivity(), false);
        mAuthorsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuthorsRecycler.setAdapter(mAdapter);
        mAuthorsRecycler.getAdapter().notifyDataSetChanged();
    }

}
