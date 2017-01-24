package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.view.NoItemLayoutView;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public interface BooksFragmentView extends BaseContextView, NoItemLayoutView {

    void setAdapter(List<Book> books);

}
