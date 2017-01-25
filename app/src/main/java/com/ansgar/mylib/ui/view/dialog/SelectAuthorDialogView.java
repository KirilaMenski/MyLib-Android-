package com.ansgar.mylib.ui.view.dialog;

import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirill on 25.1.17.
 */
public interface SelectAuthorDialogView extends BaseContextView {

    void setAdapter(List<Author> authors);
}
