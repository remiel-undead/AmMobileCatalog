package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SubsectionItem implements Parcelable {
    private int itemID;
    private String name;

    public SubsectionItem(int itemID, String name) {
        this.itemID = itemID;
        this.name = name;
    }

    protected SubsectionItem(Parcel in) {
        itemID = in.readInt();
        name = in.readString();
    }

    public static final Creator<SubsectionItem> CREATOR = new Creator<SubsectionItem>() {
        @Override
        public SubsectionItem createFromParcel(Parcel in) {
            return new SubsectionItem(in);
        }

        @Override
        public SubsectionItem[] newArray(int size) {
            return new SubsectionItem[size];
        }
    };

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(itemID);
        dest.writeString(name);
    }
}
