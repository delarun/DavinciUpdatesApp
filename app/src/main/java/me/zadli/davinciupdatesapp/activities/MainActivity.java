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

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import me.zadli.davinciupdatesapp.BuildConfig;
import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.fragments.AdditionallyFragment;
import me.zadli.davinciupdatesapp.fragments.FirmwaresFragment;
import me.zadli.davinciupdatesapp.fragments.KernelsFragment;
import me.zadli.davinciupdatesapp.fragments.ModsFragment;
import me.zadli.davinciupdatesapp.fragments.RomsFragment;
import me.zadli.davinciupdatesapp.fragments.bottom_sheets.BottomSheetDialogFragment_UpdateInfo;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_DavinciUpdatesApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("APP_DATA", Context.MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new RomsFragment()).commit(); //Load Default Fragment

        chipNavigationBar = findViewById(R.id.сhipNavigationBar_main);
        chipNavigationBar.setItemSelected(R.id.action_roms, true);
        chipNavigationBar.setOnItemSelectedListener(this::mainSwitcher);

        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
            chipNavigationBar.setBackgroundColor(getResources().getColor(R.color.background_night, getTheme())); //Set specific color in night mode

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
                String[] sortMethodArray = {getString(R.string.sort_by_date), getString(R.string.sort_by_name), getString(R.string.sort_by_json)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.choose_sort_method)
                        .setItems(sortMethodArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                switch (which){
                                    case 0:
                                        editor.putString("SORT_METHOD", "By Date");
                                        break;
                                    case 1:
                                        editor.putString("SORT_METHOD", "By Name");
                                        break;
                                    case 2:
                                        editor.putString("SORT_METHOD", "By Json");
                                        break;
                                }
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
            case R.id.action_firmwares:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_main, new FirmwaresFragment()).commit();
                break;
        }
    }

    public void checkUpdate() {
        Volley.newRequestQueue(this).add(new JsonArrayRequest(
                GET,
                "https://api.github.com/repos/zadli/DavinciUpdatesApp/releases",
                null,
                response -> {
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
                },
                error -> {

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