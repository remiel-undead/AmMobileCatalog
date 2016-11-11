package com.example.undead.ammobilecatalog.repository.orm;

import com.example.undead.ammobilecatalog.repository.CacheDataSource;
import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

public class OrmSection extends SugarRecord {
    @Unique
    int sectionID;
    String name;

    public OrmSection() {}

    public OrmSection(int sectionID, String name) {
        this.sectionID = sectionID;
        this.name = name;
    }

    public List<OrmSubsection> getSubsections() {
        return OrmSubsection.find(OrmSubsection.class,
                CacheDataSource.FIELD_SECTION + CacheDataSource.SYMBOL_EQUALITY_QUESTION,
                Integer.toString(sectionID));
    }

    public String getName() {
        return name;
    }

    public int getSectionID() {
        return sectionID;
    }
}
