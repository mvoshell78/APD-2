
package com.example.mich.allergenrecipe;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class settingsFragment extends PreferenceFragment {
    public static final String TAG = "settingsFragment";
    static ArrayList<AllergenListData> allergenList;
    static ArrayList<AllergenListData> yourAllergenList;
    EditTextPreference addAllergensPreference;
    StorePreferencesClass storePreferencesClass;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Load the preferences from our XML file.
        addPreferencesFromResource(R.xml.settings);

        storePreferencesClass = new StorePreferencesClass();
        yourAllergenList = storePreferencesClass.readFromStorage(getContext(),"yourAllergenList");

//

        for (int i=0; i< yourAllergenList.size();i++){

//            ArrayList<String> entry = new ArrayList();
//            entry.add(yourAllergenLi)
//            ListPreference listPreference = new ListPreference(getContext());
//
//            listPreference.setEntries(Integer.parseInt(yourAllergenList.get(i).getAllergenName()));
//            listPreference.setEntryValues(Integer.parseInt(yourAllergenList.get(i).getAllergenKey()));
            PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");

            CheckBoxPreference checkboxPref = new CheckBoxPreference(getContext());

            checkboxPref.setTitle(yourAllergenList.get(i).getAllergenName());
            checkboxPref.setKey(yourAllergenList.get(i).getAllergenKey());
            checkboxPref.setChecked(yourAllergenList.get(i).getBool());
            category.addPreference(checkboxPref);
            Preference myCheckbox = findPreference(yourAllergenList.get(i).getAllergenKey());
            myCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    for (int i =0; i<yourAllergenList.size(); i++){
                        if (preference.getKey().equals(yourAllergenList.get(i).getAllergenKey())){
                            yourAllergenList.get(i).setBool((Boolean) newValue);
                            storePreferencesClass.saveData(yourAllergenList, getContext(),"yourAllergenList");
                        }



                        String Checked = "";

                    }
                    return true;
                }
            });

        }
        //String allergenList[] = new String[] {"eggs","tomatoes","treeNuts"};
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

        addAllergensPreference = (EditTextPreference) findPreference("addAllergens");
        addAllergensPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String addAllergensPreferenceText = (String) newValue;
                createCheckBoxPreference(addAllergensPreferenceText);
                return false;
            }
        });

        final Preference removeAllergensPreference = getPreferenceManager().findPreference("removeAllergens");
        removeAllergensPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String removeAllergensPreferenceText = (String) newValue;
                removeCheckBoxPreference(removeAllergensPreferenceText);
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(android.R.color.background_light));

        return view;
    }


    public void createCheckBoxPreference(final String addAllergensPreferenceText){
        // adds a new check box preference

        PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");

        CheckBoxPreference checkboxPref = new CheckBoxPreference(getContext());

        checkboxPref.setTitle(addAllergensPreferenceText);
        checkboxPref.setKey(addAllergensPreferenceText);
        checkboxPref.setChecked(true);
        category.addPreference(checkboxPref);
        AllergenListData yourAllergen =  new AllergenListData(addAllergensPreferenceText,addAllergensPreferenceText.toLowerCase(),true);

        yourAllergenList.add(yourAllergen);
        storePreferencesClass.saveData(yourAllergenList,getContext(),"yourAllergenList");
        Preference myCheckbox = findPreference(addAllergensPreferenceText);
        myCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                for (int i =0; i<yourAllergenList.size(); i++){
                    if (String.valueOf(addAllergensPreferenceText.toLowerCase()).equals(String.valueOf(preference.getKey()))) {
                       yourAllergenList.get(i).setBool((Boolean) newValue);
                        storePreferencesClass.saveData(yourAllergenList,getContext(),"yourAllergenList");

                        String Checked = "";
                    }
                }

                return true;
            }
        });


    }

    public void removeCheckBoxPreference(String removeAllergensPreferenceText){
        // removes preferences from the list
        removeAllergensPreferenceText = removeAllergensPreferenceText.toLowerCase();
        PreferenceCategory category = (PreferenceCategory)findPreference("added_allergies");
        PreferenceScreen screen = getPreferenceScreen();
        Preference pref = getPreferenceManager().findPreference(removeAllergensPreferenceText);
       try{
           category.removePreference(pref);
       } catch (Exception e){
           Toast.makeText(getContext(), "There are no items in your list to remove", Toast.LENGTH_LONG).show();
           e.printStackTrace();
       }

        storePreferencesClass = new StorePreferencesClass();
        yourAllergenList = storePreferencesClass.readFromStorage(getContext(),"yourAllergenList");
        for (int i=0; i<yourAllergenList.size();i++){
            if (yourAllergenList.get(i).getAllergenKey().equals(removeAllergensPreferenceText)){
                yourAllergenList.remove(i);
            }
        }


    }


}
