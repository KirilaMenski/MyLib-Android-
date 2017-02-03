package com.ansgar.mylib.ui.presenter.fragment;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.fragment.MapFragmentView;

/**
 * Created by kirill on 3.2.17.
 */
public class MapFragmentPresenter extends BasePresenter {

    private MapFragmentView mView;

    public MapFragmentPresenter(MapFragmentView view) {
        super(view.getContext());
        mView = view;
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
