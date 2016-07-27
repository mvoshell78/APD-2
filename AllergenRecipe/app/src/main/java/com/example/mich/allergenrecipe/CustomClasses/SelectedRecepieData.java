package com.example.mich.allergenrecipe.CustomClasses;

import java.io.Serializable;

/**
 * Created by Mich on 7/25/16.
 */
public class SelectedRecepieData implements Serializable {
    private String mRecpieName;
    private String mYield;
    private String mTotalTime;
    private String mSource;
    private String mUrl;
    private String mNumberServes;
    private String mCookTime;
    private String mImageUrl;



    //creates the object
    public SelectedRecepieData(String _yield, String _totalTime, String _source, String _url, String _numberServes, String _cookTime, String _imageUrl ) {

        mYield = _yield;
        mTotalTime = _totalTime;
        mSource = _source;
        mUrl = _url;
        mNumberServes = _numberServes;
        mCookTime = _cookTime;
        mImageUrl = _imageUrl;
    }


    // Getter methods.
    public String getmYield() { return mYield;}
    public String getmTotalTime() { return mTotalTime;}
    public String getmSource() { return mSource;}
    public String getmUrl() { return mUrl;}
    public String getmNumberServes() { return mNumberServes;}
    public String getmCookTime() { return mCookTime;}
    public String getmImageUrl() { return mImageUrl;}

    // Setter Methods
    public void setmYield(String _yield) { mYield = _yield; }
    public void setmTotalTime(String _totalTime) { mTotalTime = _totalTime; }
    public void setmSource(String _source) { mSource = _source; }
    public void setmUrl(String _url) { mUrl = _url; }
    public void setmNumberServes(String _numberServes) { mNumberServes = _numberServes; }
    public void setmCookTime(String _cookTime) { mCookTime = _cookTime; }
    public void setmImageUrl(String _imageUrl) { mImageUrl = _imageUrl; }
}

