package com.ansgar.mylib.ui.presenter.dialog;

import com.ansgar.mylib.ui.base.BaseContextView;
import com.ansgar.mylib.ui.base.BasePresenter;
import com.ansgar.mylib.ui.view.dialog.RegistrationDialogView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kirill on 24.1.17.
 */
public class RegistrationDialogPresenter extends BasePresenter {

    private static final String EMAIL_PATTER = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private RegistrationDialogView mView;

    public RegistrationDialogPresenter(RegistrationDialogView view) {
        super(view.getContext());
        mView = view;
    }

    public void sendForm(String email, String password) {
        if (email.isEmpty() || !checkEmail(email)) {
            //TODO make some toast
            return;
        }
    }

    private boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTER);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public BaseContextView getView() {
        return mView;
    }
}
