package me.zadli.davinciupdatesapp.activities;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_Updater;

import static com.android.volley.Request.Method.GET;

public class UpdaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.updater));
        setContentView(R.layout.activity_updater);

        RecyclerView updater_rv = findViewById(R.id.updater_rv);
        updater_rv.setLayoutManager(new LinearLayoutManager(this));

        Volley.newRequestQueue(this).add(new JsonArrayRequest(
                GET,
                "https://api.github.com/repos/zadli/DavinciUpdatesApp/releases",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        RecyclerViewAdapter_Updater adapter_updater = new RecyclerViewAdapter_Updater(
                                UpdaterActivity.this,
                                response,
                                response.length());
                        updater_rv.setAdapter(adapter_updater);
                        updater_rv.startAnimation(AnimationUtils.loadAnimation(UpdaterActivity.this, R.anim.anim_alpha));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdaterActivity.this, String.valueOf(error), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept: ", "application/vnd.github.v3+json");
                return params;
            }
        });
    }
}