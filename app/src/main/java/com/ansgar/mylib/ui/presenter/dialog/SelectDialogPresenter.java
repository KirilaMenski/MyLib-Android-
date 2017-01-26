package com.ansgar.mylib.ui.presenter.dialog;

import com.ansgar.mylib.R;
import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.dialog.SelectDialog;
import com.ansgar.mylib.ui.view.dialog.SelectDialogView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kirill on 26.1.17.
 */
public class SelectDialogPresenter extends BasePresenter {

    private SelectDialogView mView;

    public SelectDialogPresenter(SelectDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void loadValue(String type) {
        List<String> values = new ArrayList<>();
        if (type.equals(SelectDialog.GENRE_TYPE)) {
            values = Arrays.asList(mView.getContext().getResources().getStringArray(R.array.genre_list));
        }
        if (type.equals(SelectDialog.INT_TYPE)) {
            for (int i = 0; i < 11; i++) {
                values.add(String.valueOf(i));
            }
        }
        mView.setAdapter(values);
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }

}
