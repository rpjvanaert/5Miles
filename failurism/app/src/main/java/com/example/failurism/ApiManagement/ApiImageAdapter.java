package com.example.failurism.ApiManagement;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.failurism.Activities.OnItemClickListener;
import com.example.failurism.R;

import java.util.List;

public class ApiImageAdapter extends RecyclerView.Adapter<ApiImageAdapter.ImageViewHolder>{
    private static final String LOGTAG = ApiImageAdapter.class.getName();

    private Context appContext;
    private List<ApiImage> imageList;
    private OnItemClickListener clickListener;


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            clickListener.onItemClick(clickedPosition);
        }
    }

    public ApiImageAdapter(Context context, List<ApiImage> images, OnItemClickListener listener){
        this.appContext = context;
        this.imageList = images;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d(LOGTAG, "onCreateViewHolder() called");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
//        Log.d(LOGTAG, "onBindViewHolder() called for item " + position);
        ApiImage image = imageList.get(position);
        Glide.with(appContext).load(image.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}