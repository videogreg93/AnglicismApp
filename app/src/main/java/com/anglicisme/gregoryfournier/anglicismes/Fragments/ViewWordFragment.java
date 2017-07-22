package com.anglicisme.gregoryfournier.anglicismes.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anglicisme.gregoryfournier.anglicismes.Data.DataHolder;
import com.anglicisme.gregoryfournier.anglicismes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewWordFragment extends Fragment {
    String currentWord;

    // The Views
    TextView title;
    TextView definition;


    public ViewWordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentWord = getArguments().getString("word");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_word, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get the views
        title = (TextView) getActivity().findViewById(R.id.wordview_title);
        definition = (TextView) getActivity().findViewById(R.id.wordview_definition);

        // Update the views to correct values
        title.setText(currentWord);
        Spanned sp = Html.fromHtml(DataHolder.getDefinition(currentWord));
        definition.setText(sp);

    }
}
