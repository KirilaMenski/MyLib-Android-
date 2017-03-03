package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.CitationDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.daoimpl.CitationDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.CitationAdapterListener;
import com.ansgar.mylib.ui.view.fragment.BookCitationsFragmentView;
import com.ansgar.mylib.util.DateUtils;
import com.ansgar.mylib.util.MyLibPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 30.1.17.
 */
public class BookCitationsFragmentPresenter extends BasePresenter implements CitationAdapterListener {

    private BookCitationsFragmentView mView;
    private Book mBook;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private CitationDao mCitationDao = CitationDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();
    List<Citation> mCitations = new ArrayList<>();

    public BookCitationsFragmentPresenter(BookCitationsFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(long bookId) {
        mBook = (bookId != -1) ? mBookDao.getBookById(bookId) : new Book();
    }

    public void loadCitation() {

        Observable<List<Citation>> observable = mCitationDao.getBookCitations(mBook.getId());
        Observer<List<Citation>> observer = new Observer<List<Citation>>() {
            @Override
            public void onCompleted() {
                mView.setAdapter(mCitations);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Citation> citations) {
                mCitations = citations;
            }
        };
        bindObservable(observable, observer);

    }

    public void addNewCitation(String text) {
        Citation citation = new Citation();
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        citation.setBook(mBook);
        citation.setUuid(UUID.randomUUID().toString());
        citation.setUser(user);
        citation.setCitation(text);
        citation.setDate(DateUtils.getDate());
        mCitationDao.addCitation(citation);
        mBook.setHasSynchronized(0);
        mBookDao.updateBook(mBook);
        Author author = mBook.getAuthor();
        author.setHasSynchronized(0);
        mAuthorDao.updateAuthor(author);
        loadCitation();
    }

    @Override
    public void likeCitation(Citation citation) {
        citation.setLiked(1);
        mCitationDao.updateCitation(citation);
        mView.notifyData();
    }

    @Override
    public void deleteCitation(Citation citation) {
        mCitationDao.deleteCitation(citation);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
