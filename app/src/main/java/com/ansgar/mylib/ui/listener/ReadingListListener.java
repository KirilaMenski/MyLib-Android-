package com.ansgar.mylib.ui.listener;

import com.ansgar.mylib.database.entity.Book;

/**
 * Created by kirill on 6.2.17.
 */

public interface ReadingListListener {
    void changeBookStatus(Book book, boolean wasRead);
}
