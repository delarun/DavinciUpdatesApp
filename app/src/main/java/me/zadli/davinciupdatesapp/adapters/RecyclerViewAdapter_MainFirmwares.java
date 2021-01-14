package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.IntStream;

import me.zadli.davinciupdatesapp.R;

public class RecyclerViewAdapter_MainFirmwares extends RecyclerView.Adapter<RecyclerViewAdapter_MainFirmwares.ViewHolder> {

    Context context;
    JSONObject firmwares;
    int count;
    SharedPreferences sharedPreferences;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> version = new ArrayList<>();
    int[] sortedItems;

    public RecyclerViewAdapter_MainFirmwares(Context context, JSONObject firmwares, int count) throws JSONException {
        this.context = context;
        this.firmwares = firmwares;
        this.count = count;
        sharedPreferences = context.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE);
        for (int i = 0; i < count; i++) {
            date.add(i, firmwares.getJSONObject(String.valueOf(i)).getString("date"));
            version.add(i, firmwares.getJSONObject(String.valueOf(i)).getString("version"));
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_main_firmwares, null);
        View background = view.findViewById(R.id.rv_main_firmwares_background);
        if ((parent.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
            background.setBackgroundColor(parent.getContext().getResources().getColor(R.color.background_night, parent.getContext().getTheme()));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        try {
            switch (sharedPreferences.getString("SORT_METHOD", "By Json")) {
                case "By Json":
                    setContent(holder, position);
                    break;
                case "By Name":
                    sortedItems = IntStream.range(0, count)
                            .boxed().sorted((i, j) -> version.get(j).compareTo(version.get(i)))
                            .mapToInt(ele -> ele).toArray();
                    setContent(holder, sortedItems[position]);
                    break;
                case "By Date":
                    sortedItems = IntStream.range(0, count)
                            .boxed().sorted((i, j) -> date.get(j).compareTo(date.get(i)))
                            .mapToInt(ele -> ele).toArray();
                    setContent(holder, sortedItems[position]);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setContent(@NonNull ViewHolder holder, int position) throws JSONException {
        holder.rv_main_firmwares_version.setText(firmwares.getJSONObject(String.valueOf(position)).getString("version"));
        holder.rv_main_firmwares_branch.setText(firmwares.getJSONObject(String.valueOf(position)).getString("branch"));
        holder.rv_main_firmwares_phone.setText(firmwares.getJSONObject(String.valueOf(position)).getString("phone"));
        holder.rv_main_firmwares_date.setText(firmwares.getJSONObject(String.valueOf(position)).getString("date"));
        holder.rv_main_firmwares_region.setText(firmwares.getJSONObject(String.valueOf(position)).getString("region"));
        holder.rv_main_firmwares_android_version.setText(firmwares.getJSONObject(String.valueOf(position)).getString("android_version"));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View rv_main_firmwares_background;
        TextView rv_main_firmwares_version;
        TextView rv_main_firmwares_branch;
        TextView rv_main_firmwares_phone;
        TextView rv_main_firmwares_date;
        TextView rv_main_firmwares_region;
        TextView rv_main_firmwares_android_version;
        Button rv_main_firmwares_download_link_button;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            rv_main_firmwares_background = itemView.findViewById(R.id.rv_main_firmwares_background);
            rv_main_firmwares_version = itemView.findViewById(R.id.rv_main_firmwares_version);
            rv_main_firmwares_branch = itemView.findViewById(R.id.rv_main_firmwares_branch);
            rv_main_firmwares_phone = itemView.findViewById(R.id.rv_main_firmwares_phone);
            rv_main_firmwares_date = itemView.findViewById(R.id.rv_main_firmwares_date);
            rv_main_firmwares_region = itemView.findViewById(R.id.rv_main_firmwares_region);
            rv_main_firmwares_android_version = itemView.findViewById(R.id.rv_main_firmwares_android_version);
            rv_main_firmwares_download_link_button = itemView.findViewById(R.id.rv_main_firmwares_download_link_button);

            rv_main_firmwares_download_link_button.setOnClickListener(v -> {
                try {
                    if (sharedPreferences.getString("SORT_METHOD", "By Json").equals("By Json")) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(firmwares.getJSONObject(String.valueOf(getAdapterPosition())).getString("download_link"))));
                    } else {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(firmwares.getJSONObject(String.valueOf(sortedItems[getAdapterPosition()])).getString("download_link"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
