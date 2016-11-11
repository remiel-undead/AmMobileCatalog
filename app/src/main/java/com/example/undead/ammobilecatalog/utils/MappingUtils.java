package com.example.undead.ammobilecatalog.utils;

import com.example.undead.ammobilecatalog.model.Section;
import com.example.undead.ammobilecatalog.model.Subsection;
import com.example.undead.ammobilecatalog.model.SubsectionItem;
import com.example.undead.ammobilecatalog.model.TematicSet;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsection;
import com.example.undead.ammobilecatalog.repository.orm.OrmSubsectionItem;
import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;
import com.example.undead.ammobilecatalog.repository.orm.OrmSection;

import java.util.ArrayList;
import java.util.List;

public class MappingUtils {
    public static List<OrmSection> convertIntoOrmSections(List<Section> sections) {
        List<OrmSection> ormList = new ArrayList();
        if (!ObjectUtils.isEmpty(sections)) {
            for (com.example.undead.ammobilecatalog.model.Section section: sections) {
                ormList.add(new OrmSection(section.getSectionID(), section.getName()));
            }
        }
        return ormList;
    }

    public static OrmSection convertIntoOrmSection(Section section) {
        return new OrmSection(section.getSectionID(), section.getName());
    }

    public static OrmSubsection convertIntoOrmSubsection(Subsection subsection, Section section) {
        return new OrmSubsection(subsection.getSectionID(), subsection.getName(), convertIntoOrmSection(section).getSectionID());
    }

    public static List<OrmTematicSet> convertIntoOrmTematicSets(List<TematicSet> tematicSets) {
        List<OrmTematicSet> ormList = new ArrayList();
        if (!ObjectUtils.isEmpty(tematicSets)) {
            for (TematicSet tematicSet : tematicSets) {
                ormList.add(new OrmTematicSet(tematicSet.getId(), tematicSet.getImg(), tematicSet.getDescription()));
            }
        }
        return ormList;
    }

    public static OrmTematicSet convertIntoOrmTematicSet(TematicSet tematicSet) {
        return new OrmTematicSet(tematicSet.getId(), tematicSet.getImg(), tematicSet.getDescription());
    }

    public static List<OrmSubsection> convertIntoOrmSubsections(List<Subsection> subsections,
                                                                Section section) {
        List<OrmSubsection> ormList = new ArrayList();
        if (!ObjectUtils.isEmpty(subsections)) {
            for (Subsection subsection : subsections) {
                ormList.add(new OrmSubsection(subsection.getSectionID(),
                        subsection.getName(),
                        MappingUtils.convertIntoOrmSection(section).getSectionID()));
            }
        }
        return ormList;
    }

    public static List<OrmSubsectionItem> convertIntoOrmSubsectionItems(List<SubsectionItem> subsectionItems,
                                                                    Subsection subsection, Section section) {
        List<OrmSubsectionItem> ormList = new ArrayList();
        if (!ObjectUtils.isEmpty(subsectionItems)) {
            for (SubsectionItem subsectionItem : subsectionItems) {
                ormList.add(new OrmSubsectionItem(subsectionItem.getItemID(),
                        subsection.getName(),
                        MappingUtils.convertIntoOrmSubsection(subsection, section).getSectionID()));
            }
        }
        return ormList;
    }
}
