package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.CitationDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.CitationDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.CitationAdapterListener;
import com.ansgar.mylib.ui.view.fragment.BookCitationsFragmentView;
import com.ansgar.mylib.util.DateUtils;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.List;

/**
 * Created by kirill on 30.1.17.
 */
public class BookCitationsFragmentPresenter extends BasePresenter implements CitationAdapterListener {

    private BookCitationsFragmentView mView;
    private Book mBook;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private CitationDao mCitationDao = CitationDaoImpl.getInstance();

    public BookCitationsFragmentPresenter(BookCitationsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(Book book) {
        mBook = (book != null) ? book : new Book();
    }

    public void loadCitation() {
        List<Citation> citations = mBook.getBookCitations();
        mView.setAdapter(citations);
    }

    public void addNewCitation(String text) {
        Citation citation = new Citation();
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        citation.setBook(mBook);
        citation.setUser(user);
        citation.setCitation(text);
        citation.setDate(DateUtils.getDate());
        mCitationDao.addCitation(citation);
        loadCitation();
    }

    @Override
    public void updateCitation(Citation citation) {

    }

    @Override
    public void deleteCitation(Citation citation) {
        mCitationDao.deleteCitation(citation);
        loadCitation();
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
