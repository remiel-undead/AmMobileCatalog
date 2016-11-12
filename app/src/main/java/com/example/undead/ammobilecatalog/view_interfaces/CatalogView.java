package com.example.undead.ammobilecatalog.view_interfaces;

import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;

import java.util.List;

public interface CatalogView {
    void setSectionList(List<OrmSection> sections);
    void setSubsectionList(List<OrmSubsection> subsections);
    void setSubsectionItemsList(List<OrmSubsectionItem> subsectionItems);
}
