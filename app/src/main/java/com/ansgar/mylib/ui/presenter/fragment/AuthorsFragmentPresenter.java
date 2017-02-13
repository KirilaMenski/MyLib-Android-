package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SortDialog;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.listener.SortDialogListener;
import com.ansgar.mylib.ui.view.fragment.AuthorsFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */
public class AuthorsFragmentPresenter extends BasePresenter implements SortDialogListener {

    private AuthorsFragmentView mView;
    private AuthorDao mAuthorsDao = AuthorDaoImpl.getInstance();
    private List<Author> mAllAuthors = new ArrayList<>();

    public AuthorsFragmentPresenter(AuthorsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadAuthors(final int pos) {
        mView.setProgressBarVis(true);
        Observable<List<Author>> observable = mAuthorsDao.getUserAuthors();
        Observer<List<Author>> observer = new Observer<List<Author>>() {
            @Override
            public void onCompleted() {
                switch (pos) {
                    case 0:
                        Collections.sort(mAllAuthors, new Author() {
                            @Override
                            public int compare(Author o1, Author o2) {
                                String lastName1 = o1.getLastName().toLowerCase().trim();
                                String lastName2 = o2.getLastName().toLowerCase().trim();
                                return lastName1.compareTo(lastName2);
                            }
                        });
                        break;
                    case 1:
                        Collections.sort(mAllAuthors, new Author());
                        break;
                    case 2:
                        Collections.sort(mAllAuthors, new Author() {
                            @Override
                            public int compare(Author o1, Author o2) {
                                return (o2.getAuthorBooks().size() - o1.getAuthorBooks().size());
                            }
                        });
                        break;
                }
                mView.setProgressBarVis(false);
                if (mAllAuthors.size() == 0) {
                    mView.setLayoutVisibility(true);
                } else {
                    mView.setLayoutVisibility(false);
                    mView.setAuthorAdapter(mAllAuthors);
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Author> authors) {
                mAllAuthors = authors;
            }
        };
        bindObservable(observable, observer);
    }

    public void replaceFragment(){
        selectAuthorBooks(MyLibPreference.getAuthorId());
    }

    public void selectAuthorBooks(int authorId) {
        FragmentUtil.replaceFragment((FragmentActivity) mView.getActivity(), R.id.author_book_container_layout, BooksFragment.newInstance(authorId, false, true), false);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void sortTypePosition(int pos) {
        MyLibPreference.saveAuthorSortType(pos);
        loadAuthors(pos);
    }
}
