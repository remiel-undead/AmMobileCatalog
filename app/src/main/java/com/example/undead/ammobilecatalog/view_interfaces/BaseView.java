package com.example.undead.ammobilecatalog.view_interfaces;

public interface BaseView {
    void showErrorMessage(String errorMsg);
    void showProgress();
    void hideProgress();
    void showEmptyMessage();
}
