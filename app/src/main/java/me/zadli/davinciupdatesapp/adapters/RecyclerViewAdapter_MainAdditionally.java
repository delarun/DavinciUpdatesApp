package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import me.zadli.davinciupdatesapp.R;

public class RecyclerViewAdapter_MainAdditionally extends RecyclerView.Adapter<RecyclerViewAdapter_MainAdditionally.ViewHolder> {

    Context context;
    JSONObject response;
    int count;

    public RecyclerViewAdapter_MainAdditionally(Context context, JSONObject response, int count) {
        this.context = context;
        this.response = response;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_main_additionally, null);
        View background = view.findViewById(R.id.rv_main_additionally_background);
        if ((parent.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            background.setBackgroundColor(parent.getContext().getResources().getColor(R.color.background_night));
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Picasso.with(context)
                    .load(response.getJSONObject(String.valueOf(position)).getString("additionally_image"))
                    .resize(1368, 1024)
                    .centerInside()
                    .into(holder.rv_main_additionally_image);
            holder.rv_main_additionally_name.setText(response.getJSONObject(String.valueOf(position)).getString("additionally_name"));
            holder.rv_main_additionally_version.setText(response.getJSONObject(String.valueOf(position)).getString("additionally_version"));
            holder.rv_main_additionally_build_date.setText(response.getJSONObject(String.valueOf(position)).getString("upload_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rv_main_additionally_name;
        TextView rv_main_additionally_build_date;
        TextView rv_main_additionally_version;
        ImageView rv_main_additionally_image;
        Button rv_main_additionally_download_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_main_additionally_name = itemView.findViewById(R.id.rv_main_additionally_name);
            rv_main_additionally_build_date = itemView.findViewById(R.id.rv_main_additionally_build_date);
            rv_main_additionally_version = itemView.findViewById(R.id.rv_main_additionally_version);
            rv_main_additionally_image = itemView.findViewById(R.id.rv_main_additionally_image);
            rv_main_additionally_download_button = itemView.findViewById(R.id.rv_main_additionally_download_button);

            rv_main_additionally_download_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(response.getJSONObject(String.valueOf(getAdapterPosition())).getString("download_link"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}