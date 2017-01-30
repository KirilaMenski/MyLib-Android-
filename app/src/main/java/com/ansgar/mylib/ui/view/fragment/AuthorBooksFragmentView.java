package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirila on 30.1.17.
 */
public interface AuthorBooksFragmentView extends BaseContextView {

    void setAuthorBooksAdapter(List<Book> books);
}
