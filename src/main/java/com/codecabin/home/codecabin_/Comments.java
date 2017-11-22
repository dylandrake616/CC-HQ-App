package com.codecabin.home.codecabin_;

import java.io.Serializable;

public class Comments implements Serializable {
    private String mTitle;
    private String mDate;
    private String mModified;
    private String mExcerpt;
    private String mLink;
    private String mID;
    private String mContent;

    public String getID() {
        return mID;
    }
    public void setID(String mID) {
        this.mID = mID;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getDate() {
        return mDate;
    }
    public void setDate(String mDate) {
        this.mDate = mDate;
    }
    public String getModified() {
        return mModified;
    }
    public void setModified(String mModified) {
        this.mModified = mModified;
    }
    public String getExcerpt() {
        return mExcerpt;
    }
    public void setExcerpt(String mExcerpt) {
        this.mExcerpt = mExcerpt;
    }
    public String getContent() { return mContent; }
    public void setContent(String mContent) {
        this.mContent = mContent;
    }
    public String getLink() {
        return mLink;
    }
    public void setLink(String mLink) {
        this.mLink = mLink;
    }
}