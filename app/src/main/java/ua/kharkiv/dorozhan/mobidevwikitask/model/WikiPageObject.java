package ua.kharkiv.dorozhan.mobidevwikitask.model;

import android.os.Parcel;
import android.os.Parcelable;

public class WikiPageObject implements Parcelable{
    private String pageId;
    private String title;

    public WikiPageObject() {
    }

    public WikiPageObject (Parcel source) {
        this.pageId = source.readString();
        this.title = source.readString();
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageId);
        dest.writeString(this.title);
    }

    public static final Parcelable.Creator<WikiPageObject> CREATOR =
            new Parcelable.Creator<WikiPageObject>() {

        public WikiPageObject createFromParcel(Parcel in) {
            return new WikiPageObject(in);
        }

        public WikiPageObject[] newArray(int size) {
            return new WikiPageObject[size];
        }
    };
}
