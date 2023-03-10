package com.example.vibe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.vibe.R;
import com.example.vibe.model.Music;

import java.util.List;

public class Music_Adapter extends RecyclerView.Adapter<Music_Adapter.MusicHolder> {
    List<Music> list;

    public Music_Adapter(List<Music> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Music_Adapter.MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Music_Adapter.MusicHolder holder, int position) {

        Music music=list.get(position);
        holder.title.setText(music.getTitle());
        String dur=music.getDuration()+"Minutes";
        holder.duration.setText(dur);
        Glide.with(holder.itemView.getContext())
                .load(music.getAlbum())
                .error(R.drawable.ic_baseline_music_note_24)
                .placeholder(R.drawable.ic_baseline_music_note_24)
                .into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MusicHolder extends RecyclerView.ViewHolder{
        ImageView icon;
        TextView title,duration;
        public MusicHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.item_img);
            title=itemView.findViewById(R.id.item_title);
            duration=itemView.findViewById(R.id.item_duration);


        }
    }

}
