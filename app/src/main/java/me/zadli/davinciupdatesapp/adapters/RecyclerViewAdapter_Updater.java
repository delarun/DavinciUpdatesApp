package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import me.zadli.davinciupdatesapp.R;

public class RecyclerViewAdapter_Updater  extends RecyclerView.Adapter<RecyclerViewAdapter_Updater.ViewHolder> {

    Context context;
    JSONArray response;
    int count;

    public RecyclerViewAdapter_Updater(Context context, JSONArray response, int count) {
        this.context = context;
        this.response = response;
        this.count = count;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.rv_updater, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_Updater.ViewHolder holder, int position) {
        try {
            holder.rv_updater_name.setText(response.getJSONObject(position).getString("name"));
            holder.rv_updater_tag_name.setText(response.getJSONObject(position).getString("tag_name"));
            holder.rv_updater_date.setText(response.getJSONObject(position).getString("published_at"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
                        Intent open_browser = new Intent(Intent.ACTION_VIEW);
                        open_browser.setData(Uri.parse(response.getJSONObject(getAdapterPosition()).getString("html_url")));
                        context.startActivity(open_browser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
