package goid.kotajambi.puskesmas_ngadu.view.menu_fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.ButterKnife;
import goid.kotajambi.puskesmas_ngadu.R;
import goid.kotajambi.puskesmas_ngadu.view.menu.menu_lapor;
import maes.tech.intentanim.CustomIntent;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_news extends Fragment {


    public fragment_news() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_data, container, false);
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);


        return view;


    }

}
