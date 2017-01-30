package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;

/**
 * Created by kirill on 25.1.17.
 */
public interface AddBookFragmentView extends BaseContextView {

    void setGenreEditVisibility(boolean vis);

    void setCoverBook(String cover);

    void setAuthorName(String authorName);

    void setBookResPath(String path);

    void setBookTitle(String title);

    void setBookPublished(String published);

    void setBookGenre(String genre);

    void setSeries(String series);

    void setNumbSeries(String numbSeries);

    void setDescription(String description);
}
