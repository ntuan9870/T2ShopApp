package com.example.t2shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t2shop.Model.Voucher;
import com.example.t2shop.R;

import java.util.ArrayList;

public class ChatBotAdapter extends RecyclerView.Adapter<ChatBotAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> arrMessages;

    public ChatBotAdapter(Context context, ArrayList<String> arrMessages) {
        this.context = context;
        this.arrMessages = arrMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_bot_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_chat_bot.setText(""+arrMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return arrMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_chat_bot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_chat_bot = itemView.findViewById(R.id.txt_chat_bot);
        }
    }
}
