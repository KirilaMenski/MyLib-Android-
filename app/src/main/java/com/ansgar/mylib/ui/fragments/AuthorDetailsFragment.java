package com.ansgar.mylib.ui.fragments;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseFragment;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.presenter.fragment.AuthorDetailsFragmentPresenter;
import com.ansgar.mylib.ui.view.fragment.AuthorDetailsFragmentView;
import com.squareup.picasso.Picasso;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 27.1.17.
 */

public class AuthorDetailsFragment extends BaseFragment implements AuthorDetailsFragmentView {

    private static final int LAYOUT = R.layout.fragment_author_details;
    private static final String EXTRA_AUTHOR = "com.ansgar.mylib.ui.fragments.author";

    private AuthorDetailsFragmentPresenter mPresenter;

    @BindView(R.id.author_img)
    ImageView mAuthorImage;
    @BindView(R.id.author_name)
    TextView mAuthorName;
    @BindView(R.id.author_biography)
    TextView mAuthorBiography;


    public static AuthorDetailsFragment newInstance(Author author) {
        AuthorDetailsFragment fragment = new AuthorDetailsFragment();
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
        Author author = (Author) getArguments().getSerializable(EXTRA_AUTHOR);
        mPresenter.initializeViews(author);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.author_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_author:
                mPresenter.editAuthor();
                break;
            case R.id.delete_author:
                mPresenter.deleteAuthor();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void createPresenter() {
        mPresenter = new AuthorDetailsFragmentPresenter(this);
    }

    @Override
    public void setScreenTitle(String title) {
        MainActivity activity = (MainActivity) getActivity();
        activity.setScreenTitle(title);
    }

    @Override
    public void setAuthorImage(Bitmap img) {
        mAuthorImage.setImageBitmap(img);
    }

    @Override
    public void setAuthorName(String name) {
        mAuthorName.setText(name);
    }

    @Override
    public void setAuthorBiography(String biography) {
        mAuthorBiography.setText(biography);
    }
}
