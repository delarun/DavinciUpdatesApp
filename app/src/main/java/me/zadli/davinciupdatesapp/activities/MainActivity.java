package me.zadli.davinciupdatesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import me.zadli.davinciupdatesapp.BuildConfig;
import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.fragments.AdditionallyFragment;
import me.zadli.davinciupdatesapp.fragments.KernelsFragment;
import me.zadli.davinciupdatesapp.fragments.ModsFragment;
import me.zadli.davinciupdatesapp.fragments.RomsFragment;
import me.zadli.davinciupdatesapp.fragments.bottom_sheets.BottomSheetDialogFragment_UpdateInfo;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ChipNavigationBar chipNavigationBar;
    String[] sortMethodArray = {"By Date", "By Name", "By Json"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("APP_DATA", Context.MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new RomsFragment()).commit(); //Load Default Fragment

        chipNavigationBar = findViewById(R.id.сhipNavigationBar_main);
        chipNavigationBar.setItemSelected(R.id.action_roms, true);

        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
            chipNavigationBar.setBackgroundColor(getResources().getColor(R.color.background_night, getTheme())); //Set specific color in night mode

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                mainSwitcher(i);
            }
        });

        checkUpdate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_updater:
                startActivity(new Intent(this, UpdaterActivity.class));
                break;
            case R.id.action_sort_method:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choose_sort_method)
                        .setItems(sortMethodArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("SORT_METHOD", sortMethodArray[which]);
                                editor.apply();
                                mainSwitcher(chipNavigationBar.getSelectedItemId());

                            }
                        });
                builder.create().show();
                break;
        }
        return true;
    }

    public void mainSwitcher(int action_id) {
        switch (action_id) {
            case R.id.action_roms:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new RomsFragment()).commit();
                break;
            case R.id.action_mods:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new ModsFragment()).commit();
                break;
            case R.id.action_additionally:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new AdditionallyFragment()).commit();
                break;
            case R.id.action_kernels:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new KernelsFragment()).commit();
                break;
        }
    }

    public void checkUpdate() {
        Volley.newRequestQueue(this).add(new JsonArrayRequest(
                GET,
                "https://api.github.com/repos/zadli/DavinciUpdatesApp/releases",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (!response.getJSONObject(0).getString("tag_name").equals(BuildConfig.VERSION_NAME)) {
                                Bundle bundle = new Bundle();
                                bundle.putString("JSONArray", String.valueOf(response));
                                BottomSheetDialogFragment_UpdateInfo bottomSheetDialogFragment_updateInfo = new BottomSheetDialogFragment_UpdateInfo();
                                bottomSheetDialogFragment_updateInfo.setArguments(bundle);
                                bottomSheetDialogFragment_updateInfo.show(getSupportFragmentManager(), "Updater");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept: ", "application/vnd.github.v3+json");
                return params;
            }
        });
    }
}