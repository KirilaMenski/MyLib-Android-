package com.ansgar.mylib.ui.view.fragment;

import android.graphics.Bitmap;

import com.ansgar.mylib.ui.base.BaseContextView;

import java.io.File;

/**
 * Created by kirill on 25.1.17.
 */
public interface AddBookFragmentView extends BaseContextView {

    void setCoverBytes(String coverBytes);

    void setGenreEditVisibility(boolean vis);

    void setCoverBook(Bitmap cover);

    void setAuthorName(String authorName);

    void setBookResPath(String path);

    void setBookTitle(String title);

    void setBookPublished(String published);

    void setBookGenre(String genre);

    void setSeries(String series);

    void setNumbSeries(String numbSeries);

    void setDescription(String description);

    void updatePhotoView(File path);
}
