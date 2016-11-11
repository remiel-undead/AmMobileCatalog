package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.model.Catalog;
import com.example.undead.ammobilecatalog.model.Section;
import com.example.undead.ammobilecatalog.model.TematicSet;

public class CacheDataSource implements DataStorage {
    @Override
    public Catalog getCatalog() {
        return null;
    }

    @Override
    public Section[] getSections() {
        return new Section[0];
    }

    @Override
    public TematicSet[] getTematicSets() {
        return new TematicSet[0];
    }
}
