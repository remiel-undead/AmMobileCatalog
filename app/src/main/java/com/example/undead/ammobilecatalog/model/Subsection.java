package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Subsection implements Parcelable {
    private int sectionID;
    private String name;
    private List<SubsectionItem> items;

    public Subsection(int sectionID, String name, List<SubsectionItem> items) {
        this.sectionID = sectionID;
        this.name = name;
        this.items = items;
    }

    protected Subsection(Parcel in) {
        sectionID = in.readInt();
        name = in.readString();
        items = in.createTypedArrayList(SubsectionItem.CREATOR);
    }

    public static final Creator<Subsection> CREATOR = new Creator<Subsection>() {
        @Override
        public Subsection createFromParcel(Parcel in) {
            return new Subsection(in);
        }

        @Override
        public Subsection[] newArray(int size) {
            return new Subsection[size];
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

    public List<SubsectionItem> getItems() {
        return items;
    }

    public void setItems(List<SubsectionItem> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sectionID);
        dest.writeString(name);
        dest.writeTypedList(items);
    }
}
