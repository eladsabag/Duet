package com.example.duet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.duet.data.Song;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity;
    private ArrayList<Song> songs = new ArrayList<>();

    public SongAdapter(Activity activity, ArrayList<Song> songs){
        this.activity = activity;
        this.songs = songs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ccc", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_songs, parent, false);
        SongHolder songHolder = new SongHolder(view);
        return songHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("ccc", "onBindViewHolder= " + position);
        final SongHolder holder = (SongHolder) viewHolder;
        Song song = songs.get(position);

        //int resourceId = activity.getResources().getIdentifier(song.getImage(), "drawable", activity.getPackageName());
        //holder.image.setImageResource(resourceId);
        SetImage(holder.image,song.getImage());
        holder.song.setText(song.getSong());
        holder.artist.setText(song.getArtist());

        if (song.isSelected()) {
            holder.artist.setTextColor(Color.RED);
        } else {
            holder.artist.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CheckBox checkbox;
        private MaterialTextView song;
        private MaterialTextView artist;

        public SongHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkBox);
            image = itemView.findViewById(R.id.songs_IMG_image);
            song = itemView.findViewById(R.id.songs_LBL_trackName);
            artist = itemView.findViewById(R.id.songs_LBL_artistName);
        }

    }

    private void SetImage(ImageView img, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        img.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                img.setImageResource(R.drawable.ic_launcher_background);
            }
        });
        requestQueue.add(request);
    }

}
