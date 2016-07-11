


// Michael Voshell
// MDF3 - 1606
// WeatherData.java
package com.example.mich.allergenrecipe;

import java.io.Serializable;

/**
 * Created by Mich on 6/14/16.
 */
public class RecipeData implements Serializable {

    // Member variables
    private String mCondititon;
    private String mValue1;
    private String mValue2;
    private String mImageUrl;


    //creates the object
    public RecipeData(String _condititon, String _value1, String _value2, String _imageUrl) {

        mCondititon = _condititon;
        mValue1 = _value1;
        mValue2 = _value2;
        mImageUrl = _imageUrl;

    }


    // Getter methods.
    public String getCondition() { return mCondititon;}
    public String getValue1() { return mValue1;}
    public String getValue2() { return mValue2;}
    public String getImageUrl() { return mImageUrl;}

    // Setter Methods
    public void setCondition(String _condititon) { mCondititon = _condititon; }
    public void setValue1(String _value1) { mValue1 = _value1; }
    public void setValue2(String _value2) { mValue2 = _value2; }
    public void setImageUrl(String _imageUrl) { mImageUrl = _imageUrl; }
}
