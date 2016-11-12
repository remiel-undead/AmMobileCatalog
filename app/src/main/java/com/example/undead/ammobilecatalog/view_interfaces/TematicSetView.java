package com.example.undead.ammobilecatalog.view_interfaces;

import com.example.undead.ammobilecatalog.repository.orm.OrmTematicSet;

import java.util.List;

public interface TematicSetView {
    void setTematicSetsList(List<OrmTematicSet> tematicSets);
}