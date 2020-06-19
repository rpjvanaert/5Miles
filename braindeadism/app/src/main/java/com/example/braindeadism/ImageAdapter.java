package com.example.braindeadism;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private static final String LOGTAG = ImageAdapter.class.getName();

    private Context appContext;
    private List<String> urlList;

    public ImageAdapter(Context context, List<String> content){
        this.appContext = context;
        this.urlList = content;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(LOGTAG, "onCreateViewHOlder() called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Log.d(LOGTAG, "onBindViewHolder() called for item " + position);
        Glide.with(appContext).load(urlList.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemIV);
        }
    }
}
