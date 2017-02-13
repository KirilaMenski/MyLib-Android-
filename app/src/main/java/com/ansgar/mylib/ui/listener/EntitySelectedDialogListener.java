package com.ansgar.mylib.ui.listener;

import com.ansgar.mylib.database.entity.Book;

/**
 * Created by kirill on 25.1.17.
 */

public interface EntitySelectedDialogListener {

    void authorSelected(int authorId, String firstName, String lastName);

    void bookSelected(int bookId);

}
