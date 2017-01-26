package com.ansgar.mylib.ui.view.dialog;

import com.ansgar.mylib.ui.base.BaseContextView;

import java.util.List;

/**
 * Created by kirill on 26.1.17.
 */
public interface SelectDialogView extends BaseContextView {

    void setAdapter(List<String> value);

}
