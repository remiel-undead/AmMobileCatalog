package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;

import java.util.List;

public interface DataStorage {
    List<OrmSection> getSections();
    List<OrmTematicSet> getTematicSets();
    List<OrmSubsection> getSubsections(int sectionId);
    List<OrmSubsectionItem> getSubsectionItems(int sectionId);

}
