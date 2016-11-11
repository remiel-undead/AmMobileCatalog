package com.example.undead.ammobilecatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ResponseObject implements Parcelable {
    private Catalog catalog;

    public ResponseObject(Catalog catalog) {
        this.catalog = catalog;
    }

    protected ResponseObject(Parcel in) {
        catalog = in.readParcelable(Catalog.class.getClassLoader());
    }

    public static final Creator<ResponseObject> CREATOR = new Creator<ResponseObject>() {
        @Override
        public ResponseObject createFromParcel(Parcel in) {
            return new ResponseObject(in);
        }

        @Override
        public ResponseObject[] newArray(int size) {
            return new ResponseObject[size];
        }
    };

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(catalog, flags);
    }
}
