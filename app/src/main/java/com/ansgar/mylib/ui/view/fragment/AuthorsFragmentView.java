package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.database.entity.Author;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.view.NoItemLayoutView;

import java.util.List;

/**
 * Created by kirill on 24.1.17.
 */
public interface AuthorsFragmentView extends BaseContextView, NoItemLayoutView {

    void setAuthorAdapter(List<Author> authors);

}
