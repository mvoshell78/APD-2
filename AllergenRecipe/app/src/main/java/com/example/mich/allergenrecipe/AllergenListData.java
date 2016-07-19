package com.example.mich.allergenrecipe;

import java.io.Serializable;

/**
 * Created by Mich on 7/18/16.
 */
public class AllergenListData implements Serializable {

    private String mAllergenName;
    private String mAllergenKey;
    private Boolean mBool;



    //creates the object
    public AllergenListData(String _allergenName, String _allergenKey, Boolean _bool) {

        mAllergenName = _allergenName;
        mAllergenKey = _allergenKey;
        mBool = _bool;


    }


    // Getter methods.
    public String getAllergenName() { return mAllergenName;}
    public String getAllergenKey() { return mAllergenKey;}
    public Boolean getBool() { return mBool;}


    // Setter Methods
    public void setAllergenName(String _allergenName) { mAllergenName = _allergenName; }
    public void setAllergenKey(String _allergenKey) { mAllergenKey = _allergenKey; }
    public void setBool(Boolean _bool) { mBool = _bool; }

}
