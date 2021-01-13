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
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainAdditionally;

import static com.android.volley.Request.Method.GET;

public class AdditionallyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_additionally, container, false);

        RecyclerView main_additionally_rv = view.findViewById(R.id.main_additionally_rv);

        main_additionally_rv.setLayoutManager(new LinearLayoutManager(container.getContext()));

        Volley.newRequestQueue(Objects.requireNonNull(container.getContext())).add(new JsonObjectRequest(
                GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/additionally.json",
                null,
                response -> {
                    try {
                        JSONObject additionally = response.getJSONObject("additionally");
                        RecyclerViewAdapter_MainAdditionally adapter_mainAdditionally = new RecyclerViewAdapter_MainAdditionally(
                                container.getContext(),
                                additionally,
                                additionally.length()
                        );
                        main_additionally_rv.setAdapter(adapter_mainAdditionally);
                        main_additionally_rv.startAnimation(AnimationUtils.loadAnimation(container.getContext(), R.anim.anim_alpha));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }));

        return view;
    }
}