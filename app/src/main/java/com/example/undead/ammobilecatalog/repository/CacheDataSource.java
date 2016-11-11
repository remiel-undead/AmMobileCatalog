package com.example.undead.ammobilecatalog.repository;

import com.example.undead.ammobilecatalog.repository.orm.OrmSection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;
import com.example.undead.ammobilecatalog.utils.ObjectUtils;

import java.util.List;

public class CacheDataSource implements DataStorage {
    public static final String FIELD_SECTION = "section";
    public static final String FIELD_SECTION_ID = "sectionID";
    public static final String FIELD_SET_ID = "setId";
    public static final String FIELD_ITEM_ID = "itemId";
    public static final String FIELD_SUBSECTION = "subsection";
    public static final String FIELD_ITEMS = "items";
    public static final String SYMBOL_EQUALITY_QUESTION = " = ?";

    @Override
    public List<OrmSection> getSections() {
        return OrmSection.listAll(OrmSection.class);
    }

    @Override
    public List<OrmTematicSet> getTematicSets() {
        return OrmTematicSet.listAll(OrmTematicSet.class);
    }

    @Override
    public List<OrmSubsection> getSubsections(int sectionId) {
        List<OrmSection> sections = OrmSection.find(OrmSection.class, FIELD_SECTION_ID + SYMBOL_EQUALITY_QUESTION, Integer.toString(sectionId));
        if (!ObjectUtils.isEmpty(sections) && sections.get(0) != null) {
            return sections.get(0).getSubsections();
        } else {
            return null;
        }
    }

    @Override
    public List<OrmSubsectionItem> getSubsectionItems(int sectionId) {
        List<OrmSubsection> subsections = OrmSubsection.find(OrmSubsection.class, FIELD_SECTION_ID + SYMBOL_EQUALITY_QUESTION, Integer.toString(sectionId));
        if (!ObjectUtils.isEmpty(subsections) && subsections.get(0) != null) {
            return subsections.get(0).getSubsectionItems();
        } else {
            return null;
        }
    }

    public void updateSections(List<OrmSection> ormSections) {
        if (!ObjectUtils.isEmpty(ormSections)) {
            for (OrmSection section: ormSections){
                section.update();
            }
        }
    }

    public void updateSubsections(List<OrmSubsection> ormSubsections){
        if (!ObjectUtils.isEmpty(ormSubsections)) {
            for (OrmSubsection subsection : ormSubsections){
                subsection.update();
            }
        }
    }

    public void updateSubsectionItems(List<OrmSubsectionItem> ormSubsectionItems){
        if (!ObjectUtils.isEmpty(ormSubsectionItems)) {
            for (OrmSubsectionItem subsectionItem : ormSubsectionItems){
                subsectionItem.update();
            }
        }
    }

    public void updateTematicSets(List<OrmTematicSet> ormTematicSets) {
        if (!ObjectUtils.isEmpty(ormTematicSets)) {
            for (OrmTematicSet ormTematicSet : ormTematicSets){
                ormTematicSet.update();
            }
        }
    }
}
