package com.example.undead.ammobilecatalog.bus;

import com.example.undead.ammobilecatalog.model.Catalog;

public class LoginPerformedEvent {
    public final Catalog catalog;

    public LoginPerformedEvent(Catalog catalog) {
        this.catalog = catalog;
    }
}
