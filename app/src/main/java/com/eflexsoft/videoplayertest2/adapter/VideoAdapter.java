package com.eflexsoft.videoplayertest2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eflexsoft.videoplayertest2.R;
import com.eflexsoft.videoplayertest2.VideoActivity;
import com.eflexsoft.videoplayertest2.model.VideoModel;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Holder> {

    List<VideoModel> videoModelList;
    Context context;

    public VideoAdapter(List<VideoModel> videoModelList, Context context) {
        this.videoModelList = videoModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.video_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Glide.with(context).load("file://"+videoModelList.get(position).getVideoThumbnail()).skipMemoryCache(false).into(holder.thumb);


    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView thumb;

        public Holder(@NonNull View itemView) {
            super(itemView);

            thumb = itemView.findViewById(R.id.tumbnaill_image);
            thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, VideoActivity.class).putExtra("video",videoModelList.get(getAdapterPosition()).getVideoPath()));
                }
            });

        }
    }
}
