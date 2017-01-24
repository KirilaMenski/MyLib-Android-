package com.ansgar.mylib.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by kirill on 24.1.17.
 */

public interface BaseContextView {

    Activity getActivity();

    Context getContext();

    void startActivity(Intent intent);

    void hideKeyBoard();

}
