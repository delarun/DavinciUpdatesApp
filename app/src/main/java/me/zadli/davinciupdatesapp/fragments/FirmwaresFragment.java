package me.zadli.davinciupdatesapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainFirmwares;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainKernels;

import static com.android.volley.Request.Method.GET;

public class FirmwaresFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_firmwares, container, false);
        RecyclerView main_firmwares_rv = view.findViewById(R.id.main_firmwares_rv);

        main_firmwares_rv.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Volley.newRequestQueue(Objects.requireNonNull(container.getContext())).add(new JsonObjectRequest(
                GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/firmwares.json",
                null,
                response -> {
                    try {
                        JSONObject firmwares = response.getJSONObject("firmwares");
                        RecyclerViewAdapter_MainFirmwares adapter_mainFirmwares = new RecyclerViewAdapter_MainFirmwares(
                                container.getContext(),
                                firmwares,
                                firmwares.length());
                        main_firmwares_rv.setAdapter(adapter_mainFirmwares);
                        main_firmwares_rv.startAnimation(AnimationUtils.loadAnimation(container.getContext(), R.anim.anim_alpha));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }));

        return view;
    }
}