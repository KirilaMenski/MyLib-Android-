package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AuthorsFragment;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.view.fragment.ProfileFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 24.1.17.
 */
public class ProfileFragmentPresenter extends BasePresenter {

    public static final String AUTHOR_FRAGMENT = "author_fragment";
    public static final String BOOK_FRAGMENT = "book_fragment";

    private ProfileFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private User mUser;

    public ProfileFragmentPresenter(ProfileFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView() {
        mUser = mUserDao.getUserById(MyLibPreference.getUserId());
        mView.setScreenTitle(mUser.getFirstName() + "\n" + mUser.getLastName());
        mView.setUserAvatar(mUser.getCoverBytes());
        mView.setUserEmail(mUser.getEmail());
//        mView.setAuthorCount(mView.getActivity().getString(R.string.authors_count, mUser.getAuthors().size()));
//        mView.setBookCount(mView.getActivity().getString(R.string.books_count, mUser.getBooks().size()));
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    public void openFragment(String fragment) {
        //TODO
        MainActivity activity = (MainActivity) mView.getActivity();
        if (fragment.equals(AUTHOR_FRAGMENT)) {
            MyLibPreference.saveCurrentLibPage(0);
            FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                    R.id.main_fragment_container, AuthorsFragment.newInstance(),
                    false, R.anim.right_out, R.anim.left_out);
            activity.setFooterVis(true);
        }
        if (fragment.equals(BOOK_FRAGMENT)) {
            MyLibPreference.saveCurrentLibPage(1);
            FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                    R.id.main_fragment_container, BooksFragment.newInstance(-1, true, false),
                    false, R.anim.right_out, R.anim.left_out);
            activity.setFooterVis(true);
        }
    }
}
