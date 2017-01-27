package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirill on 27.1.17.
 */
public interface AuthorDetailsFragmentView extends BaseContextView {
    void setAuthorBooksAdapter(List<Book> books);

    void setScreenTitle(String title);

    void setAuthorImage(String img);

    void setAuthorName(String name);

    void setAuthorBiography(String biography);
}
