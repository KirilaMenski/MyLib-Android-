package com.ansgar.mylib.ui.presenter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

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
import com.ansgar.mylib.util.BitmapCover;
import com.ansgar.mylib.util.FragmentUtil;
import com.ansgar.mylib.util.MyLibPreference;
import com.ansgar.mylib.util.PictureUtils;

import java.io.File;
import java.io.InputStream;

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

    public void initializeView(Author author) {
        mAuthor = author;
        mView.setScreenTitle(author.getFirstName() + "\n" + author.getLastName());
        mView.setAuthorImage(author.getBitmap());
        mView.setAuthorBiography(author.getBiography());
        mView.setAuthorFirstName(author.getFirstName());
        mView.setAuthorLastName(author.getLastName());
        mView.setAuthorDate(author.getDate());
    }

    public void handleAuthor(boolean isEdit, String firstName, String lastName, String date, String biography) {
        User user = mUserDao.getUserById(MyLibPreference.getUserId());
        Author author = new Author();
        if (mAuthor != null) {
            author = mAuthor;
        }
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDate(date);
        author.setBiography(biography);
        author.setUser(user);
        if (mPhoto != null) author.setCoverBytes(BitmapCover.getStringBytes(mPhoto));
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

    @Override
    public void choosePhotoClicked() {
        FileManagerDialog dialog = new FileManagerDialog(mView.getActivity(), FileManagerDialog.TYPE).setFilter(".*\\.txt");
        dialog.setListener(this);
        dialog.show();
    }
}
