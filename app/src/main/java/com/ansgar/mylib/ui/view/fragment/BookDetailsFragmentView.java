package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

import android.graphics.Bitmap;

/**
 * Created by kirill on 30.1.17.
 */
public interface BookDetailsFragmentView extends BaseContextView {

    void setScreenTitle(String title);

    void setBookCover(Bitmap cover);

    void setAuthorName(String authorName);

    void setBookTitle(String title);

    void setBookDate(String date);

    void setBookGenre(String genre);

    void setSeries(String series);

    void setDescription(String description);

    void setRatingAdapter(int rating);

}
