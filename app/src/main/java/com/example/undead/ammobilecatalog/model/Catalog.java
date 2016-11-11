package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Catalog implements Parcelable{
    private List<Section> sections;
    private List<TematicSet> tematicSets;

    public Catalog(List<Section> sections, List<TematicSet> tematicSets) {
        this.sections = sections;
        this.tematicSets = tematicSets;
    }

    protected Catalog(Parcel in) {
        sections = in.createTypedArrayList(Section.CREATOR);
        tematicSets = in.createTypedArrayList(TematicSet.CREATOR);
    }

    public static final Creator<Catalog> CREATOR = new Creator<Catalog>() {
        @Override
        public Catalog createFromParcel(Parcel in) {
            return new Catalog(in);
        }

        @Override
        public Catalog[] newArray(int size) {
            return new Catalog[size];
        }
    };

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<TematicSet> getTematicSets() {
        return tematicSets;
    }

    public void setTematicSets(List<TematicSet> tematicSets) {
        this.tematicSets = tematicSets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(sections);
        dest.writeTypedList(tematicSets);
    }
}
