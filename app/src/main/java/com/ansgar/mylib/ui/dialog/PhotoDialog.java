package com.ansgar.mylib.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.listener.PhotoDialogListener;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kirill on 3.2.17.
 */

public class PhotoDialog extends DialogFragment {

    private static final int LAYOUT = R.layout.dialog_photo;

    private Dialog mDialog;
    private WeakReference<PhotoDialogListener> mListener;

    @BindView(R.id.make_photo)
    TextView mMakePhoto;
    @BindView(R.id.choose_photo)
    TextView mChoosePhoto;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        ButterKnife.bind(this, view);
        mDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    @OnClick(R.id.make_photo)
    public void makePhoto() {
        mDialog.dismiss();
        mListener.get().makePhotoClicked();
    }

    @OnClick(R.id.choose_photo)
    public void choosePhoto() {
        mDialog.dismiss();
        mListener.get().choosePhotoClicked();
    }

    public void setListener(PhotoDialogListener listener) {
        mListener = new WeakReference<>(listener);
    }

    @Override
    public void onPause() {
        mDialog.dismiss();
        super.onPause();
    }
}
