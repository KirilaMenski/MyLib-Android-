package com.ansgar.mylib.ui.presenter.fragment;

import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.fbreader.Description;
import com.ansgar.mylib.fbreader.DescriptionImpl;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.listener.FileManagerDialogListener;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;

import java.io.InputStream;
import java.util.List;

/**
 * Created by kirill on 25.1.17.
 */
public class AddBookFragmentPresenter extends BasePresenter implements FileManagerDialogListener {

    private AddBookFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private Book mBook;

    public AddBookFragmentPresenter(AddBookFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(Book book) {
        mBook = book;
        mView.setCoverBook(book.getCover());
        mView.setAuthorName(book.getAuthor().getFirstName() + "\n" + book.getAuthor().getLastName());
        mView.setBookResPath(book.getResPath());
        mView.setBookTitle(book.getTitle());
        mView.setBookPublished(String.valueOf(book.getYear()));
        mView.setBookGenre(book.getGenre());
        mView.setSeries(book.getSeries());
        mView.setNumbSeries(String.valueOf(book.getNumSeries()));
        mView.setDescription(book.getDescription());
    }

    public void addBook(boolean isEdit, String coverBookPath, Author author, String bookResPath,
                        String bookTitle, String genre, String series, int seriesNum,
                        int year, String description) {

        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Book book = new Book();
        if (mBook != null) {
            book = mBook;
        }
        book.setCover(coverBookPath);
        book.setAuthor(author);
        book.setResPath(bookResPath);
        book.setTitle(bookTitle);
        book.setGenre(genre);
        book.setSeries(series);
        book.setNumSeries(seriesNum);
        book.setYear(year);
        book.setDescription(description);
        book.setUser(user);
        if (isEdit) {
            mBookDao.updateBook(book);
        } else {
            mBookDao.addBook(book);
        }
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void fileSelected(InputStream inputStream, String path) {
        Description description = new DescriptionImpl(inputStream);
        mView.setCoverBook(description.getCover().get(0));
//        mView.setAuthorName(book.getAuthor().getFirstName() + "\n" + book.getAuthor().getLastName()); // TODO ???
        mView.setBookResPath(path);
        mView.setBookTitle(description.getTitle());
//        mView.setBookPublished(String.valueOf(description.get));
        mView.setBookGenre(description.getGenre());
        mView.setSeries(description.getSeries());
        mView.setNumbSeries(String.valueOf(description.getNumOfSer()));
        List<String> annotation = description.getAnnotation();
        StringBuilder strDescription = new StringBuilder(" ");
        for (int i = 0; i < annotation.size(); i++) {
            strDescription.append(annotation.get(i));
        }
        mView.setDescription(strDescription.toString());
    }
}
