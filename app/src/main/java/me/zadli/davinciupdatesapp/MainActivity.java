package me.zadli.davinciupdatesapp;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import me.zadli.davinciupdatesapp.adapters.RecyclerViewAdapter_MainRoms;
import me.zadli.davinciupdatesapp.fragments.AdditionallyFragment;
import me.zadli.davinciupdatesapp.fragments.ModsFragment;
import me.zadli.davinciupdatesapp.fragments.RomsFragment;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new RomsFragment()).commit(); //Load Default Fragment

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_main);

        if((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            bottomNavigationView .setBackgroundColor(getResources().getColor(R.color.background_night));
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_roms:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new RomsFragment()).commit();
                        break;
                    case R.id.action_mods:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new ModsFragment()).commit();
                        break;
                    case R.id.action_additionally:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new AdditionallyFragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}