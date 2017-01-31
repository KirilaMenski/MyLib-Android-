package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AddBookFragment;
import com.ansgar.mylib.ui.view.fragment.AuthorBooksFragmentView;
import com.ansgar.mylib.util.FragmentUtil;

/**
 * Created by kirila on 30.1.17.
 */
public class AuthorBooksFragmentPresenter extends BasePresenter {

    private AuthorBooksFragmentView mView;
    private Author mAuthor;

    public AuthorBooksFragmentPresenter(AuthorBooksFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeViews(Author author) {
        mAuthor = author;
    }

    public void openAddBookFragment() {
        FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                R.id.main_fragment_container, AddBookFragment.newInstance(mAuthor, null),
                true, R.anim.right_out, R.anim.left_out);
    }

    public void loadAuthorBooks() {
        mView.setAuthorBooksAdapter(mAuthor.getAuthorBooks());
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
