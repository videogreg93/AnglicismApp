package com.anglicisme.gregoryfournier.anglicismes.Fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.anglicisme.gregoryfournier.anglicismes.Data.DataHolder;
import com.anglicisme.gregoryfournier.anglicismes.MainActivity;
import com.anglicisme.gregoryfournier.anglicismes.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllWordsFragment extends Fragment {
    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> words;
    MainActivity mainActivity;
    Boolean isFavorites = false;



    public AllWordsFragment() {

    }

    public void init(MainActivity m, boolean loadFavorites) {
        mainActivity = m;
        isFavorites = loadFavorites;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListView();



        // Setup Autocomplete
        autoCompleteTextView = (AutoCompleteTextView) getActivity().findViewById(R.id.autocompleteFindWord);
        autoCompleteTextView.setAdapter(new ArrayAdapter<>(getContext(),  android.R.layout.simple_dropdown_item_1line, words));
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = (String) parent.getItemAtPosition(position);
                autoCompleteTextView.setText("");
                mainActivity.goToViewWordFragment(word);
                hideKeyboardFrom(getContext(), autoCompleteTextView);
            }
        });





        Log.d("JSON", "Words size = " +  listview.getAdapter().getCount());
    }

    private void setupListView() {
        listview = (ListView) getActivity().findViewById(R.id.listViewWords);

        // Load json file
        loadJSONFromAsset();

        // Get words from Json Data
        words = new ArrayList<>();

        /*DataHolder.wordsJson.keys().forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String s) {
                words.add(s);
                Log.d("JSON", "Added " + s);
            }
        });*/
        if (isFavorites) {
            words = DataHolder.getFavoriteWords();
        } else {
            Iterator<String> iterator = DataHolder.wordsJson.keys();
            while(iterator.hasNext()) {
                words.add(iterator.next());
            }

            Collections.sort(words, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    o1 = Normalizer.normalize(o1, Normalizer.Form.NFD);
                    o2 = Normalizer.normalize(o2, Normalizer.Form.NFD);
                    o1 = o1.replace(" ", "");
                    o2 = o2.replace(" ","");
                    return o1.compareTo(o2);
                }
            });
        }



        listview.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, words));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = words.get(position);
                mainActivity.goToViewWordFragment(word);
            }
        });

    }

    public void loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("data2.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            DataHolder.wordsJson = new JSONObject(json);


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
