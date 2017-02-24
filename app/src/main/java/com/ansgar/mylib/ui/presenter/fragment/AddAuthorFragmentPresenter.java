package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.database.dao.AuthorDao;
import com.ansgar.mylib.database.dao.UserDao;
import com.ansgar.mylib.database.daoimpl.AuthorDaoImpl;
import com.ansgar.mylib.database.daoimpl.UserDaoImpl;
import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.User;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.FileManagerDialog;
import com.ansgar.mylib.ui.listener.FileManagerDialogListener;
import com.ansgar.mylib.ui.listener.PhotoDialogListener;
import com.ansgar.mylib.ui.view.fragment.AddAuthorFragmentView;
import com.ansgar.mylib.util.FileManagerUtil;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.PictureUtils;

import java.io.File;
import java.io.InputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 25.1.17.
 */
public class AddAuthorFragmentPresenter extends BasePresenter implements FileManagerDialogListener, PhotoDialogListener {

    public static final int REQUEST_PHOTO = 0;

    private AddAuthorFragmentView mView;
    private AuthorDao mAuthorDao = AuthorDaoImpl.getInstance();
    private UserDao mUserDao = UserDaoImpl.getInstance();
    private Author mAuthor;
    private File mPhotoFile;
    private Bitmap mPhoto;

    public AddAuthorFragmentPresenter(AddAuthorFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    public void initializeView(long authorId) {
        mAuthor = mAuthorDao.getAuthorById(authorId);
        mView.setScreenTitle(mAuthor.getFirstName() + "\n" + mAuthor.getLastName());
        mView.updatePhotoView(new File(mAuthor.getCoverBytes()));
        mView.setCoverPath(mAuthor.getCoverBytes());
        mView.setAuthorBiography(mAuthor.getBiography());
        mView.setAuthorFirstName(mAuthor.getFirstName());
        mView.setAuthorLastName(mAuthor.getLastName());
        mView.setAuthorDate(mAuthor.getDate());
    }

    public void handleAuthor(boolean isEdit, String firstName, String lastName, String date, String biography, String coverPath) {
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Author author = new Author();
        if (mAuthor != null) {
            author = mAuthor;
        }
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDate(date);
        author.setBiography(biography);
        author.setHasSynchronized(0);
        author.setUser(user);
        if (mPhoto != null) {
            author.setCoverBytes(FileManagerUtil.saveFile(mPhoto, author.getFirstName() + author.getLastName(), FileManagerUtil.SD_AUTHORS));
        } else {
            author.setCoverBytes(coverPath);
        }
        if (isEdit) {
            mAuthorDao.updateAuthor(author);
        } else {
            mAuthorDao.addAuthor(author);
        }
        FragmentUtil.clearBackStack((FragmentActivity) mView.getActivity(), 1);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

    @Override
    public void fileSelected(InputStream inputStream, String path) {

    }

    @Override
    public void photoSelected(String path) {
        mPhotoFile = new File(path);
        mPhoto = PictureUtils.getScaleBitmap(mPhotoFile.getPath(), mView.getActivity());
        mView.setAuthorImage(mPhoto);
    }

    @Override
    public void makePhotoClicked() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mView.startActivityForResult(intent);
    }

    @Override
    public void choosePhotoClicked() {
        FileManagerDialog dialog = new FileManagerDialog(mView.getActivity(), FileManagerDialog.TYPE).setFilter(".*\\.txt");
        dialog.setListener(this);
        dialog.show();
    }

    public void updatePhoto(Bitmap photo) {
        mPhoto = photo;
        mView.setAuthorImage(photo);
    }
}
