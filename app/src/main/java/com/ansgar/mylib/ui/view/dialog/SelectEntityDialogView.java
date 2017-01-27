package com.ansgar.mylib.ui.view.dialog;

import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.database.entity.Book;
import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirill on 25.1.17.
 */
public interface SelectEntityDialogView extends BaseContextView {

    void setAuthorsAdapter(List<Author> authors);

    void setBooksAdapter(List<Book> books);
}
