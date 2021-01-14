package me.zadli.davinciupdatesapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainMods;

import static com.android.volley.Request.Method.GET;

public class ModsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mods, container, false);

        RecyclerView main_mods_rv = view.findViewById(R.id.main_mods_rv);

        main_mods_rv.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Volley.newRequestQueue(Objects.requireNonNull(container.getContext())).add(new JsonObjectRequest(
                GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/mods.json",
                null,
                response -> {
                    try {
                        JSONObject mods = response.getJSONObject("mods");
                        RecyclerViewAdapter_MainMods adapter_mainMods = new RecyclerViewAdapter_MainMods(
                                container.getContext(),
                                mods,
                                mods.length()
                        );
                        main_mods_rv.setAdapter(adapter_mainMods);
                        main_mods_rv.startAnimation(AnimationUtils.loadAnimation(container.getContext(), R.anim.anim_alpha));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }));

        return view;
    }
}