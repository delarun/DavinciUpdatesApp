package me.zadli.davinciupdatesapp.fragments.bottom_sheets;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;

public class BottomSheetDialogFragment_UpdateInfo extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_updater_info_bottom_sheet, container, false);

        View fragment_updater_info_bottom_sheet_background = view.findViewById(R.id.fragment_updater_info_bottom_sheet_background);
        TextView fragment_updater_info_bottom_sheet_name = view.findViewById(R.id.fragment_updater_info_bottom_sheet_name);
        TextView fragment_updater_info_bottom_sheet_tag_name = view.findViewById(R.id.fragment_updater_info_bottom_sheet_tag_name);
        TextView fragment_updater_info_bottom_sheet_date = view.findViewById(R.id.fragment_updater_info_bottom_sheet_date);
        Button fragment_updater_info_bottom_sheet_open_page_button = view.findViewById(R.id.fragment_updater_info_bottom_sheet_open_page_button);

        if ((requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            fragment_updater_info_bottom_sheet_background.setBackgroundColor(requireContext().getResources().getColor(R.color.background_night, requireContext().getTheme()));
        } else {
            fragment_updater_info_bottom_sheet_background.setBackgroundColor(requireContext().getResources().getColor(R.color.white, requireContext().getTheme()));
        }

        try {
            JSONArray response = new JSONArray(Objects.requireNonNull(getArguments()).getString("JSONArray"));
            fragment_updater_info_bottom_sheet_name.setText(response.getJSONObject(0).getString("name"));
            fragment_updater_info_bottom_sheet_tag_name.setText(response.getJSONObject(0).getString("tag_name"));
            fragment_updater_info_bottom_sheet_date.setText(response.getJSONObject(0).getString("published_at"));
            fragment_updater_info_bottom_sheet_open_page_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(response.getJSONObject(0).getString("html_url"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme);
        return super.onCreateDialog(savedInstanceState);
    }
}
