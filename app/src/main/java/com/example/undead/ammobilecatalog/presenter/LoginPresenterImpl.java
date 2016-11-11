package com.example.undead.ammobilecatalog.presenter;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.ErrorEvent;
import com.example.undead.ammobilecatalog.bus.LoginEvent;
import com.example.undead.ammobilecatalog.bus.LoginPerformedEvent;
import com.example.undead.ammobilecatalog.utils.CryptUtils;
import com.example.undead.ammobilecatalog.utils.ObjectUtils;
import com.example.undead.ammobilecatalog.view_interfaces.LoginView;
import com.squareup.otto.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {
    private static final String LOGIN_PREFS = "login_prefs";
    private static final String TAG_LOGIN = "login";
    private static final String TAG_PASSWORD = "psswrd";

    private LoginView loginView;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void login() {
        SharedPreferences preferences = context.getSharedPreferences(LOGIN_PREFS, context.MODE_PRIVATE);
        String login = preferences.getString(TAG_LOGIN, null);
        String passwordEncrypted = preferences.getString(TAG_PASSWORD, null);
        if (ObjectUtils.isEmpty(login) || ObjectUtils.isEmpty(passwordEncrypted)) {
            // TODO get login and password from dialog
            /**
             * Mock for getting login and password
             */
            login = "loginarea";
            passwordEncrypted = CryptUtils.encrypt("passarea");

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(TAG_LOGIN, login);
            editor.putString(TAG_PASSWORD, passwordEncrypted);
            editor.apply();
        }
        BusProvider.getInstance().post(new LoginEvent(login, passwordEncrypted));
    }

    @Subscribe
    public void onLoginPerformed(LoginPerformedEvent loginPerformedEvent) {
        ///
        loginView.showErrorMessage("THIS IS NOT AN ERROR");
        ///
        loginView.finishSplash();
    }

    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        loginView.showErrorMessage(errorEvent.message);
    }
}
