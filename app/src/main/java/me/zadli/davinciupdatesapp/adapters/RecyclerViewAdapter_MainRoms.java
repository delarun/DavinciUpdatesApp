package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import me.zadli.davinciupdatesapp.R;
import me.zadli.davinciupdatesapp.fragments.BottomSheetDialogFragment_MainRoms;

public class RecyclerViewAdapter_MainRoms extends RecyclerView.Adapter<RecyclerViewAdapter_MainRoms.ViewHolder> {

    Context context;
    JSONObject response;
    int count;

    public RecyclerViewAdapter_MainRoms(Context context, JSONObject response, int count) {
        this.context = context;
        this.response = response;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.rv_main_roms, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MainRoms.ViewHolder holder, int position) {
        try {
            Picasso.with(context)
                    .load(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("rom_image"))
                    .resize(1368, 1024)
                    .centerInside()
                    .into(holder.rv_main_roms_rom_image);
            holder.rv_main_roms_author.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("author"));
            holder.rv_main_roms_rom_name.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("rom_name"));
            if (response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getBoolean("official")) {
                holder.rv_main_roms_official.append("Official");
            } else {
                holder.rv_main_roms_official.append("Unofficial");
            }
            holder.rv_main_roms_build_date.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("build_date"));
            holder.rv_main_roms_rom_version.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("rom_version"));
            holder.rv_main_roms_rom_codename.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("rom_codename"));
            holder.rv_main_roms_android_version.append(response.getJSONObject("roms").getJSONObject(String.valueOf(position)).getString("android_version"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView rv_main_roms_rom_image;
        TextView rv_main_roms_rom_name;
        TextView rv_main_roms_author;
        TextView rv_main_roms_official;
        TextView rv_main_roms_build_date;
        TextView rv_main_roms_rom_version;
        TextView rv_main_roms_rom_codename;
        TextView rv_main_roms_android_version;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_main_roms_rom_image = itemView.findViewById(R.id.rv_main_roms_rom_image);
            rv_main_roms_rom_name = itemView.findViewById(R.id.rv_main_roms_rom_name);
            rv_main_roms_author = itemView.findViewById(R.id.rv_main_roms_author);
            rv_main_roms_official = itemView.findViewById(R.id.rv_main_roms_official);
            rv_main_roms_build_date = itemView.findViewById(R.id.rv_main_roms_build_date);
            rv_main_roms_rom_version = itemView.findViewById(R.id.rv_main_roms_rom_version);
            rv_main_roms_rom_codename = itemView.findViewById(R.id.rv_main_roms_rom_codename);
            rv_main_roms_android_version = itemView.findViewById(R.id.rv_main_roms_android_version);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Bundle send_data = new Bundle();

                        send_data.putString("rom_image", response.getJSONObject("roms").getJSONObject(String.valueOf(getAdapterPosition())).getString("rom_image"));
                        send_data.putString("changelog_link", response.getJSONObject("roms").getJSONObject(String.valueOf(getAdapterPosition())).getString("changelog_link"));
                        send_data.putString("download_link", response.getJSONObject("roms").getJSONObject(String.valueOf(getAdapterPosition())).getString("download_link"));
                        send_data.putString("tg_author", response.getJSONObject("roms").getJSONObject(String.valueOf(getAdapterPosition())).getString("tg_author"));
                        send_data.putString("tg_group", response.getJSONObject("roms").getJSONObject(String.valueOf(getAdapterPosition())).getString("tg_group"));

                        BottomSheetDialogFragment_MainRoms bottomSheetDialogFragment_mainRoms = new BottomSheetDialogFragment_MainRoms();
                        bottomSheetDialogFragment_mainRoms.setArguments(send_data);
                        bottomSheetDialogFragment_mainRoms.show(((FragmentActivity) context).getSupportFragmentManager(), "MainBottomSheet");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
