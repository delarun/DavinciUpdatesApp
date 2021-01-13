package me.zadli.davinciupdatesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.stream.IntStream;

import me.zadli.davinciupdatesapp.R;

public class RecyclerViewAdapter_MainKernels extends RecyclerView.Adapter<RecyclerViewAdapter_MainKernels.ViewHolder> {

    Context context;
    JSONObject kernels;
    int count;
    SharedPreferences sharedPreferences;
    ArrayList<String> build_date = new ArrayList<>();
    ArrayList<String> kernel_name = new ArrayList<>();
    int[] sortedItems;

    public RecyclerViewAdapter_MainKernels(Context context, JSONObject kernels, int count) throws JSONException {
        this.context = context;
        this.kernels = kernels;
        this.count = count;
        sharedPreferences = context.getSharedPreferences("APP_DATA", Context.MODE_PRIVATE);
        for (int i = 0; i < count; i++) {
            build_date.add(i, kernels.getJSONObject(String.valueOf(i)).getString("build_date"));
            kernel_name.add(i, kernels.getJSONObject(String.valueOf(i)).getString("kernel_name"));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.rv_main_kernels, null);
        View background = view.findViewById(R.id.rv_main_kernels_background);
        if ((parent.getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
            background.setBackgroundColor(parent.getContext().getResources().getColor(R.color.background_night, parent.getContext().getTheme()));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter_MainKernels.ViewHolder holder, int position) {
        try {
            switch (sharedPreferences.getString("SORT_METHOD", "By Json")) {
                case "By Json":
                    setContent(holder, position);
                    break;
                case "By Name":
                    sortedItems = IntStream.range(0, count)
                            .boxed().sorted((i, j) -> kernel_name.get(i).compareTo(kernel_name.get(j)))
                            .mapToInt(ele -> ele).toArray();
                    setContent(holder, sortedItems[position]);
                    break;
                case "By Date":
                    sortedItems = IntStream.range(0, count)
                            .boxed().sorted((i, j) -> build_date.get(j).compareTo(build_date.get(i)))
                            .mapToInt(ele -> ele).toArray();
                    setContent(holder, sortedItems[position]);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setContent(@NonNull RecyclerViewAdapter_MainKernels.ViewHolder holder, int position) throws JSONException {
        Picasso.with(context)
                .load(kernels.getJSONObject(String.valueOf(position)).getString("kernel_image"))
                .resize(1368, 1024)
                .centerInside()
                .into(holder.rv_main_kernels_image);
        holder.rv_main_kernels_author.setText(kernels.getJSONObject(String.valueOf(position)).getString("kernel_author"));
        holder.rv_main_kernels_name.setText(kernels.getJSONObject(String.valueOf(position)).getString("kernel_name"));
        holder.rv_main_kernels_version.setText(kernels.getJSONObject(String.valueOf(position)).getString("kernel_version"));
        holder.rv_main_kernels_kernel_kernel_version.setText(kernels.getJSONObject(String.valueOf(position)).getString("kernel_kernel_version"));
        holder.rv_main_kernels_build_date.setText(kernels.getJSONObject(String.valueOf(position)).getString("build_date"));
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView rv_main_kernels_author;
        final TextView rv_main_kernels_name;
        final TextView rv_main_kernels_build_date;
        final TextView rv_main_kernels_version;
        final TextView rv_main_kernels_kernel_kernel_version;
        final ImageView rv_main_kernels_image;
        final Button rv_main_kernels_download_button;
        final Button rv_main_kernels_changelog_button;

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

            rv_main_kernels_download_button.setOnClickListener(v -> {
                try {
                    if (sharedPreferences.getString("SORT_METHOD", "By Json").equals("By Json")) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(kernels.getJSONObject(String.valueOf(getAdapterPosition())).getString("download_link"))));
                    } else {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(kernels.getJSONObject(String.valueOf(sortedItems[getAdapterPosition()])).getString("download_link"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            rv_main_kernels_changelog_button.setOnClickListener(v -> {
                try {
                    if (sharedPreferences.getString("SORT_METHOD", "By Json").equals("By Json")) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(kernels.getJSONObject(String.valueOf(getAdapterPosition())).getString("kernel_changelog_link"))));
                    } else {
                        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(kernels.getJSONObject(String.valueOf(sortedItems[getAdapterPosition()])).getString("kernel_changelog_link"))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
