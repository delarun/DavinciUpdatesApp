package me.zadli.davinciupdatesapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainRoms;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView main_roms_rv = findViewById(R.id.main_roms_rv);
        main_roms_rv.setLayoutManager(new LinearLayoutManager(this));

        Volley.newRequestQueue(this).add(new JsonObjectRequest(GET,
                "https://raw.githubusercontent.com/zadli/DavinciUpdatesApp/main/jsons/roms.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject roms = response.getJSONObject("roms");
                            RecyclerViewAdapter_MainRoms adapter_mainRoms = new RecyclerViewAdapter_MainRoms(
                                    MainActivity.this,
                                    response,
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
                }));
    }
}