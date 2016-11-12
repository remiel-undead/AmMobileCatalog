package com.example.undead.ammobilecatalog.repository.orm;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class OrmTematicSet extends SugarRecord {
    @Unique
    int setId;
    String img;
    String description;

    public OrmTematicSet() {
    }

    public OrmTematicSet(int setId, String img, String description) {
        this.setId = setId;
        this.img = img;
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public String getDescription() {
        return description;
    }
}
