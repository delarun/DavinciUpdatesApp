package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import me.zadli.davinciupdatesapp.R;

public class RecyclerViewAdapter_Updater extends RecyclerView.Adapter<RecyclerViewAdapter_Updater.ViewHolder> {

    Context context;
    JSONArray updater;
    int count;

    public RecyclerViewAdapter_Updater(Context context, JSONArray updater, int count) {
        this.context = context;
        this.updater = updater;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_updater, null);
        View background = view.findViewById(R.id.rv_updater_background);
        if ((parent.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            background.setBackgroundColor(parent.getContext().getResources().getColor(R.color.background_night));
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Updater.ViewHolder holder, int position) {
        try {
            holder.rv_updater_name.setText(updater.getJSONObject(position).getString("name"));
            holder.rv_updater_tag_name.setText(updater.getJSONObject(position).getString("tag_name"));
            holder.rv_updater_date.setText(updater.getJSONObject(position).getString("published_at"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rv_updater_name;
        TextView rv_updater_tag_name;
        TextView rv_updater_date;
        Button rv_updater_open_page_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_updater_name = itemView.findViewById(R.id.rv_updater_name);
            rv_updater_tag_name = itemView.findViewById(R.id.rv_updater_tag_name);
            rv_updater_date = itemView.findViewById(R.id.rv_updater_date);
            rv_updater_open_page_button = itemView.findViewById(R.id.rv_updater_open_page_button);

            rv_updater_open_page_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(updater.getJSONObject(getAdapterPosition()).getString("html_url"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
