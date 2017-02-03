package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AddAuthorFragment;
import com.ansgar.mylib.ui.view.fragment.AuthorDetailsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;

import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 27.1.17.
 */
public class AuthorDetailsFragmentPresenter extends BasePresenter {

    private AuthorDetailsFragmentView mView;
    private Author mAuthor;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();

    public AuthorDetailsFragmentPresenter(AuthorDetailsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeViews(Author author) {
        mAuthor = author;
        mView.setScreenTitle(author.getFirstName() + "\n" + author.getLastName());
        mView.setAuthorName(author.getFirstName() + " " + author.getLastName());
        mView.setAuthorBiography(author.getBiography());
        mView.setAuthorImage(author.getCoverBytes());
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    public void deleteAuthor() {
        mAuthorDao.deleteAuthor(mAuthor);
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    public void editAuthor() {
        FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(), R.id.main_fragment_container,
                AddAuthorFragment.newInstance(mAuthor), true, R.anim.right_out, R.anim.left_out);
    }
}
