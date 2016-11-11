package com.example.undead.ammobilecatalog.repository.orm;

import com.example.undead.ammobilecatalog.repository.CacheDataSource;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

public class OrmSubsection extends SugarRecord {
    @Unique
    int sectionID;
    String name;

    // defining a relationship
    OrmSection section;

    public OrmSubsection() {}

    public OrmSubsection(int sectionID, String name, OrmSection section) {
        this.sectionID = sectionID;
        this.name = name;
        this.section = section;
    }

    public List<OrmSubsectionItem> getSubsectionItems() {
        return OrmSubsectionItem.find(OrmSubsectionItem.class,
                CacheDataSource.FIELD_SUBSECTION + CacheDataSource.SYMBOL_EQUALITY_QUESTION,
                Integer.toString(sectionID));
    }

    public String getName() {
        return name;
    }

    public int getSectionID() {
        return sectionID;
    }
}
