package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.model.Catalog;
import com.example.undead.ammobilecatalog.model.Section;
import com.example.undead.ammobilecatalog.model.TematicSet;

public interface DataStorage {
    Catalog getCatalog();
    Section[] getSections();
    TematicSet[] getTematicSets();
}
