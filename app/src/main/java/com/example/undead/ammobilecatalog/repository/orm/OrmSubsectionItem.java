package com.example.undead.ammobilecatalog.repository.orm;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class OrmSubsectionItem extends SugarRecord {
    @Unique
    int itemID;
    String name;

    // defining a relationship
    int subsection;

    public OrmSubsectionItem() {}

    public OrmSubsectionItem(int itemID, String name, int subsection) {
        this.itemID = itemID;
        this.name = name;
        this.subsection = subsection;
    }

    public String getName() {
        return name;
    }
}
