package me.zadli.davinciupdatesapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

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
        Button fragment_main_roms_bottom_sheet_download_vanilla_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_download_vanilla_link);
        Button fragment_main_roms_bottom_sheet_mirror_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_mirror_link);
        Button fragment_main_roms_bottom_sheet_mirror_vanilla_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_mirror_vanilla_link);
        Button fragment_main_roms_bottom_sheet_donate_paypal_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_donate_paypal_link);
        Button fragment_main_roms_bottom_sheet_donate_buymeacoffee_link = view.findViewById(R.id.fragment_main_roms_bottom_sheet_donate_buymeacoffee_link);
        Button fragment_main_roms_bottom_sheet_tg_author = view.findViewById(R.id.fragment_main_roms_bottom_sheet_tg_author);
        Button fragment_main_roms_bottom_sheet_tg_group = view.findViewById(R.id.fragment_main_roms_bottom_sheet_tg_group);
        ConstraintLayout fragment_main_roms_bottom_sheet_constraint_background = view.findViewById(R.id.fragment_main_roms_bottom_sheet_constraint_background);

        if((requireContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            fragment_main_roms_bottom_sheet_constraint_background.setBackgroundColor(requireContext().getResources().getColor(R.color.background_night));
        }else{
            fragment_main_roms_bottom_sheet_constraint_background.setBackgroundColor(requireContext().getResources().getColor(R.color.white));
        }

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
        if (getArguments().getString("download_link").equals("-")){
            fragment_main_roms_bottom_sheet_download_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_download_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("download_link")));
                    startActivity(open_browser);
                }
            });
        }
        if (getArguments().getString("download_vanilla_link").equals("-")){
            fragment_main_roms_bottom_sheet_download_vanilla_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_download_vanilla_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("download_vanilla_link")));
                    startActivity(open_browser);
                }
            });
        }
        if (getArguments().getString("mirror_link").equals("-")){
            fragment_main_roms_bottom_sheet_mirror_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_mirror_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("mirror_link")));
                    startActivity(open_browser);
                }
            });
        }
        if (getArguments().getString("mirror_vanilla_link").equals("-")){
            fragment_main_roms_bottom_sheet_mirror_vanilla_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_mirror_vanilla_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("mirror_vanilla_link")));
                    startActivity(open_browser);
                }
            });
        }
        if (getArguments().getString("donate_paypal_link").equals("-")){
            fragment_main_roms_bottom_sheet_donate_paypal_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_donate_paypal_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("donate_paypal_link")));
                    startActivity(open_browser);
                }
            });
        }
        if (getArguments().getString("donate_buymeacoffee_link").equals("-")){
            fragment_main_roms_bottom_sheet_donate_buymeacoffee_link.setVisibility(View.GONE);
        }else{
            fragment_main_roms_bottom_sheet_donate_buymeacoffee_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent open_browser = new Intent(Intent.ACTION_VIEW);
                    open_browser.setData(Uri.parse(Objects.requireNonNull(getArguments()).getString("donate_buymeacoffee_link")));
                    startActivity(open_browser);
                }
            });
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
