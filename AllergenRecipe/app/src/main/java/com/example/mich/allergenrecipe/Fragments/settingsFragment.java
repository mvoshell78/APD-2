
package com.example.mich.allergenrecipe.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mich.allergenrecipe.CustomClasses.AllergenListData;
import com.example.mich.allergenrecipe.R;
import com.example.mich.allergenrecipe.Storage.StorePreferencesClass;

import java.util.ArrayList;


public class settingsFragment extends PreferenceFragment {
    public static final String TAG = "settingsFragment";
    static ArrayList<AllergenListData> yourAllergenListArray;
    EditTextPreference addAllergensPreference;
    StorePreferencesClass storePreferencesClass;
    static String[] entry;
    static String[] entryValues;
    final private String custom_list = "custom_list";
    String yourAllergenList ="yourAllergenList";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Load the preferences from our XML file.
        addPreferencesFromResource(R.xml.settings);

        storePreferencesClass = new StorePreferencesClass();
        yourAllergenListArray = storePreferencesClass.readFromStorage(getContext(),yourAllergenList);

        addToYourAllergensPreference();
        removeFromYourAllergensPreference();
        checkBoxOfAddedAllergensPreference();
        stockAllergenPreference();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        return view;
    }

    private void addToYourAllergensPreference() {
        addAllergensPreference = (EditTextPreference) findPreference("addAllergens");
        addAllergensPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String addAllergensPreferenceText = (String) newValue;
                createCheckBoxPreference(addAllergensPreferenceText);
                removeFromYourAllergensPreference();
                return false;
            }
        });
    }
    private void removeFromYourAllergensPreference() {
        storePreferencesClass = new StorePreferencesClass();
        yourAllergenListArray = storePreferencesClass.readFromStorage(getContext(),yourAllergenList);

        entry = new String[yourAllergenListArray.size()];
        entryValues = new String[yourAllergenListArray.size()];

        for (int i = 0; i< yourAllergenListArray.size(); i++){
            entry[i]= yourAllergenListArray.get(i).getAllergenName();
            entryValues[i]= yourAllergenListArray.get(i).getAllergenName();
        }


        PreferenceCategory category = (PreferenceCategory) findPreference("remove_allergen_list");
        final ListPreference lp = setListPreferenceData((ListPreference) findPreference(custom_list), getActivity());
        lp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {


                setListPreferenceData(lp, getActivity());
                return false;
            }
        });

        lp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String selectedItem =  newValue.toString();


                for (int i = 0; i< yourAllergenListArray.size(); i++){
                    if (yourAllergenListArray.get(i).getAllergenKey().equals(selectedItem)){

                        yourAllergenListArray.remove(i);
                        storePreferencesClass.saveData(yourAllergenListArray,getContext(),yourAllergenList);
                        removeFromYourAllergensPreference();
                        removeCheckBoxPreference(selectedItem);

                    }
                }
                return false;
            }
        });
        setHasOptionsMenu(true);
        category.addPreference(lp);
    }
    public void checkBoxOfAddedAllergensPreference(){
        for (int i = 0; i< yourAllergenListArray.size(); i++){

            PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");

            CheckBoxPreference checkboxPref = new CheckBoxPreference(getContext());

            checkboxPref.setTitle(yourAllergenListArray.get(i).getAllergenName());
            checkboxPref.setKey(yourAllergenListArray.get(i).getAllergenKey());
            checkboxPref.setChecked(yourAllergenListArray.get(i).getBool());
            category.addPreference(checkboxPref);
            Preference myCheckbox = findPreference(yourAllergenListArray.get(i).getAllergenKey());
            myCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    for (int i = 0; i< yourAllergenListArray.size(); i++){
                        if (preference.getKey().equals(yourAllergenListArray.get(i).getAllergenKey())){
                            yourAllergenListArray.get(i).setBool((Boolean) newValue);
                            storePreferencesClass.saveData(yourAllergenListArray, getContext(),yourAllergenList);
                        }



                        String Checked = "";

                    }
                    return true;
                }
            });

        }

    }
    public void stockAllergenPreference(){
        AllergenListData eggs =  new AllergenListData("Eggs","eggs",false);
        AllergenListData tomatoes =  new AllergenListData("Tomatoes","tomatoes",false);
        AllergenListData walnuts =  new AllergenListData("Walnuts","walnuts",false);
        AllergenListData pecans =  new AllergenListData("Pecans","pecans",false);
        AllergenListData peanuts =  new AllergenListData("Peanuts","peanuts",false);
        AllergenListData chicken =  new AllergenListData("Chicken","chicken",false);
        AllergenListData beef =  new AllergenListData("Beef","beef",false);
        AllergenListData pork =  new AllergenListData("Pork","pork",false);
        AllergenListData fish =  new AllergenListData("Fish","fish",false);
        AllergenListData crab =  new AllergenListData("Crab","crab",false);
        AllergenListData shrimp =  new AllergenListData("Shrimp","shrimp",false);
        AllergenListData shelfish =  new AllergenListData("Shellfish","shellfish",false);
        AllergenListData mussels =  new AllergenListData("Mussels","mussels",false);
        AllergenListData soy =  new AllergenListData("Soy","soy",false);
        AllergenListData wheat =  new AllergenListData("Wheat","wheat",false);
        AllergenListData milk =  new AllergenListData("Milk","milk",false);
        AllergenListData strawberries =  new AllergenListData("Strawberries","strawberries",false);


        final ArrayList<AllergenListData> allergenList = new ArrayList<>();
        allergenList.add(eggs);
        allergenList.add(tomatoes);
        allergenList.add(walnuts);
        allergenList.add(pecans);
        allergenList.add(peanuts);
        allergenList.add(chicken);
        allergenList.add(beef);
        allergenList.add(pork);
        allergenList.add(fish);
        allergenList.add(crab);
        allergenList.add(shrimp);
        allergenList.add(shelfish);
        allergenList.add(mussels);
        allergenList.add(soy);
        allergenList.add(wheat);
        allergenList.add(milk);
        allergenList.add(strawberries);

        for (int i=0; i<allergenList.size();i++) {
            PreferenceCategory category = (PreferenceCategory)findPreference("allergy_list");

            CheckBoxPreference checkboxPref = new CheckBoxPreference(getContext());

            checkboxPref.setTitle(allergenList.get(i).getAllergenName());
            checkboxPref.setKey(allergenList.get(i).getAllergenKey());
            checkboxPref.setChecked(false);
            category.addPreference(checkboxPref);
            Preference myCheckbox = findPreference(allergenList.get(i).getAllergenKey());
            myCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    for (int i=0; i<allergenList.size();i++) {
                        if (preference.getKey().equals(allergenList.get(i).getAllergenKey())){
                            allergenList.get(i).setBool((Boolean) newValue);
                            storePreferencesClass.saveData(allergenList, getContext(), "allergenList");
                        }


                    }
                    return true;
                }
            });
        }

    }

//    public void checkTheList(String addAllergensPreferenceText ){
//        yourAllergenListArray = storePreferencesClass.readFromStorage(getContext(),yourAllergenList);
//
//        if (yourAllergenListArray.size() > 0 ){
//            for (int i = 0; i< yourAllergenListArray.size(); i++){
//                if (addAllergensPreferenceText.equals(yourAllergenListArray.get(i).getAllergenKey())){
//                    Toast.makeText(MainActivity.context, addAllergensPreferenceText + " is already in your list",Toast.LENGTH_SHORT).show();
//
//                } else {
//                    createCheckBoxPreference(addAllergensPreferenceText);
//
//                }
//            }
//
//        } else {
//            createCheckBoxPreference(addAllergensPreferenceText);
//
//        }
//    }

    public void createCheckBoxPreference(final String addAllergensPreferenceText){
        // adds a new check box preference


        PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");

        CheckBoxPreference checkboxPref = new CheckBoxPreference(getContext());

        checkboxPref.setTitle(addAllergensPreferenceText);

        checkboxPref.setKey(addAllergensPreferenceText);
        checkboxPref.setChecked(true);
        category.addPreference(checkboxPref);
        AllergenListData yourAllergen =  new AllergenListData(addAllergensPreferenceText,addAllergensPreferenceText,true);
        yourAllergenListArray = storePreferencesClass.readFromStorage(getContext(),yourAllergenList);
        yourAllergenListArray.add(yourAllergen);
        storePreferencesClass.saveData(yourAllergenListArray,getContext(),yourAllergenList);
        Preference myCheckbox = findPreference(addAllergensPreferenceText);
        myCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                for (int i = 0; i< yourAllergenListArray.size(); i++){

                    if (String.valueOf(addAllergensPreferenceText).equals(String.valueOf(preference.getKey()))) {
                        yourAllergenListArray.get(i).setBool((Boolean) newValue);
                        storePreferencesClass.saveData(yourAllergenListArray,getContext(),yourAllergenList);


                    }
                }

                return true;
            }
        });
    }
    public void removeCheckBoxPreference(String removeAllergensPreferenceText){
        // removes preferences from the list
        //removeAllergensPreferenceText = removeAllergensPreferenceText.toLowerCase();
        PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");
        PreferenceScreen screen = getPreferenceScreen();
        Preference pref = getPreferenceManager().findPreference(removeAllergensPreferenceText);
       try{
           category.removePreference(pref);
       } catch (Exception e){
           Toast.makeText(getContext(), "There are no items in your list to remove", Toast.LENGTH_LONG).show();
           e.printStackTrace();
       }

//        storePreferencesClass = new StorePreferencesClass();
//        yourAllergenListArray = storePreferencesClass.readFromStorage(getContext(),yourAllergenList);
//        for (int i = 0; i< yourAllergenListArray.size(); i++){
//            if (yourAllergenListArray.get(i).getAllergenKey().equals(removeAllergensPreferenceText)){
//                yourAllergenListArray.remove(i);
//            }
//        }


    }
    protected ListPreference setListPreferenceData(ListPreference lp, Activity mActivity) {

        if(lp == null)
            lp = new ListPreference(mActivity);
        lp.setEntries(entry);
        lp.setDefaultValue("1");
        lp.setEntryValues(entryValues);
        lp.setTitle("Remove allergens from your list");
        lp.setSummary(lp.getEntry());
        lp.setDialogTitle("Select to remove");
        lp.setKey(custom_list);
        return lp;
    }

}
