package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.bus.LoginPerformedEvent;
import com.example.undead.ammobilecatalog.model.Catalog;
import com.example.undead.ammobilecatalog.model.Section;
import com.example.undead.ammobilecatalog.model.TematicSet;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class CatalogRepository implements DataStorage {
    private Bus mBus;
    private CloudDataSource mCloudDataSource;
    private CacheDataSource mCacheDataSource;

    public CatalogRepository(Bus bus) {
        mBus = bus;
        mCloudDataSource = new CloudDataSource();
        mBus.register(mCloudDataSource);
        mCacheDataSource = new CacheDataSource();
    }

    @Override
    public Catalog getCatalog() {
        return mCacheDataSource.getCatalog();
    }

    @Override
    public Section[] getSections() {
        return mCacheDataSource.getSections();
    }

    @Override
    public TematicSet[] getTematicSets() {
        return mCacheDataSource.getTematicSets();
    }

    @Subscribe
    void onLoginPerformed(LoginPerformedEvent loginPerformedEvent) {
        // TODO update bd into CacheDataSource
    }

}
