package me.zadli.davinciupdatesapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainRoms;

import static com.android.volley.Request.Method.GET;

public class RomsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roms, container, false);

        RecyclerView main_roms_rv = view.findViewById(R.id.main_roms_rv);

        main_roms_rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(new JsonObjectRequest(GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/roms.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject roms = response.getJSONObject("roms");
                            RecyclerViewAdapter_MainRoms adapter_mainRoms = new RecyclerViewAdapter_MainRoms(
                                    getActivity(),
                                    roms,
                                    roms.length());
                            main_roms_rv.setAdapter(adapter_mainRoms);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
            )
        );

        return view;
    }
}