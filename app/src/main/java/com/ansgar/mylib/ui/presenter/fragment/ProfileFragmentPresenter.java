package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.api.ApiRequest;
import com.ansgar.mylib.api.response.ServerResponse;
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
import com.ansgar.mylib.util.PictureUtils;

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
    private List<Author> mAuthors;
    private List<Book> mBooks;
    private List<Citation> mCitations;

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
                        author.setHasSynchronized(1);
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
                            book.setHasSynchronized(1);
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
                                citation.setHasSynchronized(1);
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
        }
        if (fragment.equals(BOOK_FRAGMENT)) {
            MyLibPreference.saveCurrentLibPage(1);
            FragmentUtil.replaceAnimFragment((FragmentActivity) mView.getActivity(),
                    R.id.main_fragment_container, BooksFragment.newInstance(-1, true, false, true),
                    false, R.anim.right_out, R.anim.left_out);
        }
    }

    public void save() {
        if (NetWorkUtils.isNetworkConnected(mView.getContext())) {
            User user = mUserDao.getUserById(MyLibPreference.getUserId());
            mAuthors = user.getUnSynchronizedAuthorList();
            if(mAuthors.size() == 0) {
                Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < mAuthors.size(); i++) {
                Author author = mAuthors.get(i);
                author.setHasSynchronized(1);
                mAuthorDao.updateAuthor(author);
                author.setCoverBytes(BitmapCover.getStringBytes(PictureUtils.getBitmapFromPath(mAuthors.get(i).getCoverBytes())));
                mBooks = author.getUnSynchronizedBooks();
                for (int j = 0; j < mBooks.size(); j++) {
                    Book book = mBooks.get(j);
                    book.setHasSynchronized(1);
                    mBookDao.updateBook(book);
                    book.setCoverBytes(BitmapCover.getStringBytes(PictureUtils.getBitmapFromPath(mBooks.get(j).getCoverBytes())));
                    mCitations = book.getUnSynchronizedCitations();
                    for(int z = 0; z < mCitations.size(); z++){
                        mCitations.get(z).setHasSynchronized(1);
                        mCitationDao.updateCitation(mCitations.get(z));
                    }
                    book.setCitations(mCitations);
                }
                author.setAuthorBooks(mBooks);
            }
            user.setAuthors(mAuthors);

            Observable<ServerResponse> observable = ApiRequest.getInstance().getApi().saveData(user);
            Observer<ServerResponse> observer = new Observer<ServerResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ServerResponse serverResponse) {
                    Log.i(TAG, serverResponse.getMessage());
                }
            };
            bindObservable(observable, observer);

        } else {
            Toast.makeText(mView.getContext(), mView.getContext().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
        }
    }

}
