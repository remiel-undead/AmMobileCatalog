package com.example.undead.ammobilecatalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.undead.ammobilecatalog.bus.BusProvider;
import com.example.undead.ammobilecatalog.presenter.LoginPresenter;
import com.example.undead.ammobilecatalog.presenter.LoginPresenterImpl;
import com.example.undead.ammobilecatalog.view_interfaces.LoginView;

public class SplashActivity extends AppCompatActivity implements LoginView {
    private LoginPresenter presenter;
    private boolean isLoginPosted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(this, getApplicationContext());
        isLoginPosted = false;
    }

    @Override
    public void showErrorMessage(String errorMsg) {
        Toast.makeText(SplashActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishSplash() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(presenter);
        if (!isLoginPosted) {
            presenter.login();
            isLoginPosted = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(presenter);
    }
}