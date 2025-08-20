//package com.example.deninternship_week5.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.deninternship_week5.Entity.Message;
//import com.example.deninternship_week5.R;
//
//import java.util.List;
//
//public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
//
//    private Context context;
//    private List<Message> messageList;
//    private String currentUserId;
//
//    public MessageAdapter(Context context, List<Message> messageList, String currentUserId) {
//        this.context = context;
//        this.messageList = messageList;
//        this.currentUserId = currentUserId;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Message msg = messageList.get(position);
//
//        if (msg.getSender().equals(currentUserId)) {
//            // sender (me)
//            holder.layoutSender.setVisibility(View.VISIBLE);
//            holder.layoutReceiver.setVisibility(View.GONE);
//            holder.tvSenderMessage.setText(msg.getMessage());
//        } else {
//            // receiver
//            holder.layoutReceiver.setVisibility(View.VISIBLE);
//            holder.layoutSender.setVisibility(View.GONE);
//            holder.tvReceiverMessage.setText(msg.getMessage());
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return messageList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout layoutSender, layoutReceiver;
//        TextView tvSenderMessage, tvReceiverMessage;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            layoutSender = itemView.findViewById(R.id.layoutSender);
//            layoutReceiver = itemView.findViewById(R.id.layoutReceiver);
//            tvSenderMessage = itemView.findViewById(R.id.tvSenderMessage);
//            tvReceiverMessage = itemView.findViewById(R.id.tvReceiverMessage);
//        }
//    }
//}

package com.example.deninternship_week5.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deninternship_week5.Entity.Message;
import com.example.deninternship_week5.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<Message> messageList;
    private String currentUserId;

    public MessageAdapter(Context context, List<Message> messageList, String currentUserId) {
        this.context = context;
        this.messageList = messageList;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = messageList.get(position);

        // format timestamp to readable time, e.g., 10:45 PM
        String time = "";
        if (msg.getTimestamp() != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            time = sdf.format(new Date(msg.getTimestamp()));
        }

        if (msg.getSender().equals(currentUserId)) {
            // sender (me)
            holder.layoutSender.setVisibility(View.VISIBLE);
            holder.layoutReceiver.setVisibility(View.GONE);
            holder.tvSenderMessage.setText(msg.getMessage());
            holder.tvSenderTime.setText(time);
        } else {
            // receiver
            holder.layoutReceiver.setVisibility(View.VISIBLE);
            holder.layoutSender.setVisibility(View.GONE);
            holder.tvReceiverMessage.setText(msg.getMessage());
            holder.tvReceiverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutSender, layoutReceiver;
        TextView tvSenderMessage, tvReceiverMessage, tvSenderTime, tvReceiverTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutSender = itemView.findViewById(R.id.layoutSender);
            layoutReceiver = itemView.findViewById(R.id.layoutReceiver);
            tvSenderMessage = itemView.findViewById(R.id.tvSenderMessage);
            tvReceiverMessage = itemView.findViewById(R.id.tvReceiverMessage);
            tvSenderTime = itemView.findViewById(R.id.tvSenderTime);
            tvReceiverTime = itemView.findViewById(R.id.tvReceiverTime);
        }
    }
}
