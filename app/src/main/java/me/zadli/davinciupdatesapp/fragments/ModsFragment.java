package me.zadli.davinciupdatesapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainMods;

public class ModsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mods, container, false);

        RecyclerView main_mods_rv = view.findViewById(R.id.main_mods_rv);
        main_mods_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter_MainMods adapter_mainMods = new RecyclerViewAdapter_MainMods();
        main_mods_rv.setAdapter(adapter_mainMods);
        main_mods_rv.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha));

        return view;
    }
}