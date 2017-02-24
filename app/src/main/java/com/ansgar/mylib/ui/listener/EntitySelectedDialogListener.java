package com.ansgar.mylib.ui.listener;

/**
 * Created by kirill on 25.1.17.
 */

public interface EntitySelectedDialogListener {

    void authorSelected(long authorId, String firstName, String lastName);

    void bookSelected(long bookId);

}
