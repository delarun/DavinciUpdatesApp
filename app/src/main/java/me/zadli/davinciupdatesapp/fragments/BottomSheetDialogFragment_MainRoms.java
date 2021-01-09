package me.zadli.davinciupdatesapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;

public class BottomSheetDialogFragment_MainRoms extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_roms_bottom_sheet, container, false);
        ImageView fragment_main_roms_bottom_sheet_rom_image = view.findViewById(R.id.fragment_main_roms_bottom_sheet_rom_image);
        Button fragment_main_roms_bottom_sheet_changelog_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_changelog_link);
        Button fragment_main_roms_bottom_sheet_download_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_download_link);
        Button fragment_main_roms_bottom_sheet_tg_author = view.findViewById(R.id.fragment_main_roms_bottom_sheet_tg_author);
        Button fragment_main_roms_bottom_sheet_tg_group = view.findViewById(R.id.fragment_main_roms_bottom_sheet_tg_group);

        Picasso.with(requireContext())
                .load(Objects.requireNonNull(getArguments()).getString("rom_image"))
                .resize(1368, 1024)
                .centerInside()
                .into(fragment_main_roms_bottom_sheet_rom_image);

        fragment_main_roms_bottom_sheet_changelog_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_browser = new Intent(Intent.ACTION_VIEW);
                open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("changelog_link")));
                startActivity(open_browser);
            }
        });
        fragment_main_roms_bottom_sheet_download_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_browser = new Intent(Intent.ACTION_VIEW);
                open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("download_link")));
                startActivity(open_browser);
            }
        });
        fragment_main_roms_bottom_sheet_tg_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_browser = new Intent(Intent.ACTION_VIEW);
                open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("tg_author")));
                startActivity(open_browser);
            }
        });
        fragment_main_roms_bottom_sheet_tg_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_browser = new Intent(Intent.ACTION_VIEW);
                open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("tg_group")));
                startActivity(open_browser);
            }
        });
        return view;
    }
}
