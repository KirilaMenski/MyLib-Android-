package com.ansgar.mylib.ui.view.fragment;

import com.ansgar.mylib.database.entity.Citation;
import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirill on 30.1.17.
 */
public interface BookCitationsFragmentView extends BaseContextView {

    void setAdapter(List<Citation> citations);

}
