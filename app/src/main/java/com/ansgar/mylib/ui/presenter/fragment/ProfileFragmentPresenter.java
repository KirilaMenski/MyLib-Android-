package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.api.ApiRequest;
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
import com.ansgar.mylib.ui.activities.MainActivity;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.fragments.AuthorsFragment;
import com.ansgar.mylib.ui.fragments.BooksFragment;
import com.ansgar.mylib.ui.view.fragment.ProfileFragmentView;
import com.ansgar.mylib.util.BitmapCover;
import com.ansgar.mylib.util.DateUtils;
import com.ansgar.mylib.util.FileManagerUtil;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.NetWorkUtils;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import rx.Observable;
import rx.Observer;

/**
 * Created by kirill on 24.1.17.
 */
public class ProfileFragmentPresenter extends BasePresenter {

    private static final String TAG = ProfileFragmentPresenter.class.getSimpleName();

    public static final String AUTHOR_FRAGMENT = "author_fragment";
    public static final String BOOK_FRAGMENT = "book_fragment";

    private ProfileFragmentView mView;
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private CitationDao mCitationDao = CitationDaoImpl.getInstance();
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

    public void synchronizeData() {

        if (NetWorkUtils.isNetworkConnected(mView.getContext())) {
            final User user = mUserDao.getUserById(MyLibPreference.getUserId());
            mView.setProgressBarVis(true);
            Observable<User> observable = ApiRequest.getInstance().getApi().synchronizeData(MyLibPreference.getUserId());
            Observer<User> observer = new Observer<User>() {
                @Override
                public void onCompleted() {
                    mView.setProgressBarVis(false);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(User user) {
                    List<Author> authors = user.getAuthors();
                    for (int i = 0; i < authors.size(); i++) {
                        Author author = new Author();
                        author.setFirstName(authors.get(i).getFirstName());
                        author.setLastName(authors.get(i).getLastName());
                        author.setCoverBytes(FileManagerUtil.saveFile(BitmapCover.getBitmapCover(authors.get(i).getCoverBytes()),
                                authors.get(i).getFirstName() + authors.get(i).getLastName() + DateUtils.getNewFileDate(), FileManagerUtil.SD_AUTHORS));
                        author.setBiography(authors.get(i).getBiography());
                        author.setDate(authors.get(i).getDate());
                        author.setUser(user);
                        mAuthorDao.addAuthor(author);
                        List<Book> books = user.getAuthors().get(i).getAuthorBooks();
                        for (int j = 0; j < books.size(); j++) {
                            Book book = new Book();
                            book.setUser(user);
                            book.setAuthor(author);
                            book.setTitle(books.get(j).getTitle());
                            book.setDescription(books.get(j).getDescription());
                            book.setCoverBytes(FileManagerUtil.saveFile(BitmapCover.getBitmapCover(books.get(j).getCoverBytes()),
                                    books.get(j).getTitle() + DateUtils.getNewFileDate(), FileManagerUtil.SD_BOOKS));
                            book.setGenre(books.get(j).getGenre());
                            book.setInList(books.get(j).getInList());
                            book.setWasRead(books.get(j).getWasRead());
                            book.setSeries(books.get(j).getSeries());
                            book.setNumSeries(books.get(j).getNumSeries());
                            book.setRating(books.get(j).getRating());
                            book.setYear(books.get(j).getYear());
                            mBookDao.addBook(book);
                            List<Citation> citations = user.getAuthors().get(i).getAuthorBooks().get(j).getCitations();
                            for (int z = 0; z < citations.size(); z++) {
                                Citation citation = new Citation();
                                citation.setUser(user);
                                citation.setBook(book);
                                citation.setCitation(citations.get(z).getCitation());
                                citation.setDate(citations.get(z).getDate());
                                citation.setLiked(citations.get(z).getLiked());
                                mCitationDao.addCitation(citation);
                            }
                        }
                    }
                }
            };
            bindObservable(observable, observer);
        } else {
            Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
        }

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
