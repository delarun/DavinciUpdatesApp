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

public class RecyclerViewAdapter_MainKernels extends RecyclerView.Adapter<RecyclerViewAdapter_MainKernels.ViewHolder> {

    Context context;
    JSONObject response;
    int count;

    public RecyclerViewAdapter_MainKernels(Context context, JSONObject response, int count) {
        this.context = context;
        this.response = response;
        this.count = count;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_main_kernels, null);
        View background = view.findViewById(R.id.rv_main_kernels_background);
        if ((parent.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            background.setBackgroundColor(parent.getContext().getResources().getColor(R.color.background_night));
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MainKernels.ViewHolder holder, int position) {
        try {
            Picasso.with(context)
                    .load(response.getJSONObject(String.valueOf(position)).getString("kernel_image"))
                    .resize(1368, 1024)
                    .centerInside()
                    .into(holder.rv_main_kernels_image);
            holder.rv_main_kernels_author.setText(response.getJSONObject(String.valueOf(position)).getString("kernel_author"));
            holder.rv_main_kernels_name.setText(response.getJSONObject(String.valueOf(position)).getString("kernel_name"));
            holder.rv_main_kernels_version.setText(response.getJSONObject(String.valueOf(position)).getString("kernel_version"));
            holder.rv_main_kernels_kernel_kernel_version.setText(response.getJSONObject(String.valueOf(position)).getString("kernel_kernel_version"));
            holder.rv_main_kernels_build_date.setText(response.getJSONObject(String.valueOf(position)).getString("build_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rv_main_kernels_author;
        TextView rv_main_kernels_name;
        TextView rv_main_kernels_build_date;
        TextView rv_main_kernels_version;
        TextView rv_main_kernels_kernel_kernel_version;
        ImageView rv_main_kernels_image;
        Button rv_main_kernels_download_button;
        Button rv_main_kernels_changelog_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_main_kernels_author = itemView.findViewById(R.id.rv_main_kernels_author);
            rv_main_kernels_name = itemView.findViewById(R.id.rv_main_kernels_name);
            rv_main_kernels_build_date = itemView.findViewById(R.id.rv_main_kernels_build_date);
            rv_main_kernels_version = itemView.findViewById(R.id.rv_main_kernels_version);
            rv_main_kernels_image = itemView.findViewById(R.id.rv_main_kernels_image);
            rv_main_kernels_kernel_kernel_version = itemView.findViewById(R.id.rv_main_kernels_kernel_kernel_version);
            rv_main_kernels_download_button = itemView.findViewById(R.id.rv_main_kernels_download_button);
            rv_main_kernels_changelog_button = itemView.findViewById(R.id.rv_main_kernels_changelog_button);

            rv_main_kernels_download_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(response.getJSONObject(String.valueOf(getAdapterPosition())).getString("download_link"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            rv_main_kernels_changelog_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(response.getJSONObject(String.valueOf(getAdapterPosition())).getString("kernel_changelog_link"))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
