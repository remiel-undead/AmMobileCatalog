package com.example.undead.ammobilecatalog.presenter;


import android.content.Context;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.bus.ErrorEvent;
import com.example.undead.ammobilecatalog.bus.LoginEvent;
import com.example.undead.ammobilecatalog.bus.LoginPerformedEvent;
import com.example.undead.ammobilecatalog.utils.SharedPrefUtils;
import com.example.undead.ammobilecatalog.view_interfaces.LoginView;
import com.squareup.otto.Subscribe;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void login() {
        String login = SharedPrefUtils.getSharedPrefLogin(context);
        String passwordEncrypted = SharedPrefUtils.getSharedPrefPasswordEncrypted(context);
        BusProvider.getInstance().post(new LoginEvent(login, passwordEncrypted));
    }

    @Subscribe
    public void onLoginPerformed(LoginPerformedEvent loginPerformedEvent) {
        loginView.finishSplash();
    }

    @Subscribe
    public void onError(ErrorEvent errorEvent) {
        loginView.showErrorMessage(errorEvent.message);
    }
}
