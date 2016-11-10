package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TematicSet implements Parcelable {
    private int id;
    private String img;
    private String description;

    public TematicSet(int id, String img, String description) {
        this.id = id;
        this.img = img;
        this.description = description;
    }

    protected TematicSet(Parcel in) {
        id = in.readInt();
        img = in.readString();
        description = in.readString();
    }

    public static final Creator<TematicSet> CREATOR = new Creator<TematicSet>() {
        @Override
        public TematicSet createFromParcel(Parcel in) {
            return new TematicSet(in);
        }

        @Override
        public TematicSet[] newArray(int size) {
            return new TematicSet[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(img);
        dest.writeString(description);
    }
}
