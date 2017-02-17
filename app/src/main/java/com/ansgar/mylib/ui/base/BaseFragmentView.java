package com.ansgar.mylib.ui.base;

import com.ansgar.mylib.ui.activities.MainActivity;

import android.support.v4.app.FragmentActivity;

/**
 * Created by kirill on 24.1.17.
 */

public interface BaseFragmentView extends BaseContextView {

    FragmentActivity getActivity();

    MainActivity getMainActivity();

}
