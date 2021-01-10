package me.zadli.davinciupdatesapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainKernels;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainMods;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainRoms;

import static com.android.volley.Request.Method.GET;

public class KernelsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kernels, container, false);

        RecyclerView main_kernels_rv = view.findViewById(R.id.main_kernels_rv);
        main_kernels_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        Volley.newRequestQueue(Objects.requireNonNull(getActivity())).add(new JsonObjectRequest(GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/kernels.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject kernels = response.getJSONObject("kernels");
                            RecyclerViewAdapter_MainKernels adapter_mainKernels = new RecyclerViewAdapter_MainKernels(
                                    getActivity(),
                                    kernels,
                                    kernels.length());
                            main_kernels_rv.setAdapter(adapter_mainKernels);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));

        return view;
    }
}