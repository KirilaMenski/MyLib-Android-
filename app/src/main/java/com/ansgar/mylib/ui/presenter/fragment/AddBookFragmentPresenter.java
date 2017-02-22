package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.R;
import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.BookDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.reader.fbreader.Description;
import com.ansgar.mylib.reader.fbreader.DescriptionImpl;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.FileManagerDialog;
import com.ansgar.mylib.ui.listener.FileManagerDialogListener;
import com.ansgar.mylib.ui.listener.PhotoDialogListener;
import com.ansgar.mylib.ui.view.fragment.AddBookFragmentView;
import com.ansgar.mylib.util.BitmapCover;
import com.ansgar.mylib.util.DateUtils;
import com.ansgar.mylib.util.FileManagerUtil;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.PictureUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 25.1.17.
 */
public class AddBookFragmentPresenter extends BasePresenter implements FileManagerDialogListener, PhotoDialogListener {

    public static final int REQUEST_PHOTO = 0;

    private AddBookFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private Book mBook;
    private File mPhotoFile;
    private Bitmap mPhoto;

    public AddBookFragmentPresenter(AddBookFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(int authorId, int bookId) {
        mBook = mBookDao.getBookById(bookId);

        mView.setCoverBytes(mBook.getCoverBytes());
//        mView.setCoverBook(mBook.getBitmap());
        mView.updatePhotoView(new File(mBook.getCoverBytes()));
        mView.setAuthorName(mBook.getAuthor().getFirstName() + "\n" + mBook.getAuthor().getLastName());
        if (authorId != -1) {
            Author author = mAuthorDao.getAuthorById(authorId);
            mView.setAuthorNameById(author.getFirstName() + "\n" + author.getLastName());
        }
        mView.setBookResPath(mBook.getResPath());
        mView.setBookTitle(mBook.getTitle());
        mView.setBookPublished(String.valueOf(mBook.getYear()));
        mView.setBookGenre(mBook.getGenre());
        mView.setSeries(mBook.getSeries());
        mView.setNumbSeries(String.valueOf(mBook.getNumSeries()));
        mView.setDescription(mBook.getDescription());
    }

    public void addBook(boolean isEdit, String coverBookPath, int authorId, String bookResPath,
                        String bookTitle, String genre, String series, int seriesNum,
                        int year, String description) {
        Author author = mAuthorDao.getAuthorById(authorId);
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Book book = new Book();
        if (mBook != null) {
            book = mBook;
        }
        book.setAuthor(author);
        book.setResPath(bookResPath);
        book.setTitle(bookTitle);
        book.setGenre(genre);
        book.setSeries(series);
        book.setNumSeries(seriesNum);
        book.setYear(year);
        book.setHasSynchronized(0);
        book.setDescription(description);
        book.setUser(user);
        if (mPhoto != null) {
//            book.setCoverBytes(BitmapCover.getStringBytes(mPhoto));
            book.setCoverBytes(FileManagerUtil.saveFile(mPhoto, bookTitle + DateUtils.getNewFileDate(), FileManagerUtil.SD_BOOKS));
        } else if (coverBookPath != null) {
//            book.setCoverBytes(coverBookPath);
            book.setCoverBytes(FileManagerUtil.saveFile(BitmapCover.getBitmapCover(coverBookPath), bookTitle + DateUtils.getNewFileDate(), FileManagerUtil.SD_BOOKS));
        } else {
//            book.setCoverBytes(BitmapCover.getStringBytes
//                    (BitmapFactory.decodeResource(mView.getContext().getResources(), R.drawable.default_book_image)));
            book.setCoverBytes("");
        }
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
        String coverBytes = (description.getCover().size() > 0) ? description.getCover().get(0)
                : BitmapCover.getStringBytes(BitmapFactory.decodeResource(mView.getContext().getResources(), R.drawable.default_book_image));
        mView.setCoverBytes(coverBytes);
        mView.setCoverBook(BitmapCover.getBitmapCover(coverBytes));
//        mView.setAuthorName(book.getAuthor().getFirstName() + "\n" + book.getAuthor().getLastName()); // TODO ???
        mView.setBookResPath(path);
        mView.setBookTitle(description.getTitle());
        mView.setBookPublished(String.valueOf(description.getYear()));
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

    @Override
    public void photoSelected(String path) {
        mPhotoFile = new File(path);
        mPhoto = PictureUtils.getScaleBitmap(mPhotoFile.getPath(), mView.getActivity());
        mView.setCoverBook(mPhoto);
        mView.setCoverBytes(BitmapCover.getStringBytes(mPhoto));
    }

    @Override
    public void makePhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mView.startActivityForResult(intent);
    }

    public void updatePhoto(Bitmap bitmap) {
        mPhoto = bitmap;
        mView.setCoverBook(mPhoto);
    }

    @Override
    public void choosePhotoClicked() {
        FileManagerDialog dialog = new FileManagerDialog(mView.getActivity(), FileManagerDialog.TYPE).setFilter(".*\\.txt");
        dialog.setListener(this);
        dialog.show();
    }

}
