package com.example.duet;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duet.data.Chat;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity;
    private ArrayList<Chat> chats = new ArrayList<>();

    public ChatAdapter(Activity activity, ArrayList<Chat> chats){
        this.activity = activity;
        this.chats = chats;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ccc", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat, parent, false);
        ChatHolder chatHolder = new ChatHolder(view);
        return chatHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("ccc", "onBindViewHolder= " + position);
        final ChatHolder holder = (ChatHolder) viewHolder;
        Chat chat = chats.get(position);

        int resourceId = activity.getResources().getIdentifier(chat.getImage(), "drawable", activity.getPackageName());
        holder.image.setImageResource(resourceId);
        holder.name.setText(chat.getName() + position);
        holder.message.setText(chat.getLastMessage());

        if (chat.isSelected()) {
            holder.message.setTextColor(Color.RED);
        } else {
            holder.message.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private MaterialTextView name;
        private MaterialTextView message;

        public ChatHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
        }

    }
}
