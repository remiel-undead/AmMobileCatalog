package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Section implements Parcelable {
    private int sectionID;
    private String name;
    private List<Subsection> subsections;

    public Section(int sectionID, String name, List<Subsection> subsections) {
        this.sectionID = sectionID;
        this.name = name;
        this.subsections = subsections;
    }

    protected Section(Parcel in) {
        sectionID = in.readInt();
        name = in.readString();
        subsections = in.createTypedArrayList(Subsection.CREATOR);
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
        }
    };

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subsection> getSubsections() {
        return subsections;
    }

    public void setSubsections(List<Subsection> subsections) {
        this.subsections = subsections;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sectionID);
        dest.writeString(name);
        dest.writeTypedList(subsections);
    }
}
