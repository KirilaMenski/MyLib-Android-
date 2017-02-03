package com.ansgar.mylib.ui.presenter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

import com.ansgar.mylib.database.dao.BookDao;
import com.ansgar.mylib.database.dao.UserDao;
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
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.PictureUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by kirill on 25.1.17.
 */
public class AddBookFragmentPresenter extends BasePresenter implements FileManagerDialogListener, PhotoDialogListener {

    public static final int REQUEST_PHOTO = 0;

    private AddBookFragmentView mView;
    private BookDao mBookDao = BookDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private Book mBook;
    private File mPhotoFile;
    private Bitmap mPhoto;

    public AddBookFragmentPresenter(AddBookFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(Book book) {
        mBook = book;
        mView.setCoverBytes(book.getCoverBytes());
        mView.setCoverBook(book.getBitmap());
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
        book.setCoverBytes(coverBookPath);
        book.setAuthor(author);
        book.setResPath(bookResPath);
        book.setTitle(bookTitle);
        book.setGenre(genre);
        book.setSeries(series);
        book.setNumSeries(seriesNum);
        book.setYear(year);
        book.setDescription(description);
        book.setUser(user);
        if (mPhoto != null) book.setCoverBytes(BitmapCover.getStringBytes(mPhoto));
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
        mView.setCoverBytes(description.getCover().get(0));
        mView.setCoverBook(BitmapCover.getBitmapCover(description.getCover().get(0)));
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
        mView.updatePhotoView(mPhotoFile);
    }

    @Override
    public void makePhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        boolean canTAkePhoto = mPhotoFile != null;
//        if (canTAkePhoto) {
//            Uri uri = Uri.fromFile(mPhotoFile);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        }
        mView.getActivity().startActivityForResult(intent, REQUEST_PHOTO);
    }

    public void updatePhoto(Bitmap bitmap) {
        mPhoto = bitmap;
        mView.updatePhotoView(mPhotoFile);
    }

    @Override
    public void choosePhotoClicked() {
        FileManagerDialog dialog = new FileManagerDialog(mView.getActivity(), FileManagerDialog.TYPE).setFilter(".*\\.txt");
        dialog.setListener(this);
        dialog.show();
    }

}
