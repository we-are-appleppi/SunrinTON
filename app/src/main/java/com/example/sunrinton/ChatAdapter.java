package com.example.sunrinton;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunrinton.Chat;
import com.example.sunrinton.R;

import java.util.ArrayList;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    ArrayList<Chat> items;
    String myname;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeright, timeleft, message;
        LinearLayout LL;
        ImageView pro_img;
        View view_onimg;

        public ViewHolder(View itemView) {
            super(itemView);
            view_onimg = itemView.findViewById(R.id.chat_item_img_profile_topview);
            LL = itemView.findViewById(R.id.chat_item_LL);
            timeleft = itemView.findViewById(R.id.chat_item_tv_timeleft);
            timeright = itemView.findViewById(R.id.chat_item_tv_timeright);
            message = itemView.findViewById(R.id.chat_item_tv_message);
        }
    }

    public ChatAdapter(ArrayList<Chat> items, String myname) {
        this.items = items;
        this.myname = myname;

    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chatting_listitem, viewGroup, false);
        ChatAdapter.ViewHolder vh = new ChatAdapter.ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull ChatAdapter.ViewHolder vh, int i) {
        Chat chat = items.get(i);

        if (i > 0) { //메시지 같은사람이 보냈을때 사람이름 중복처리
            if (chat.getSender().equals(items.get(i - 1).getSender())) {//같은사람이면
                vh.view_onimg.setVisibility(View.GONE);
                vh.pro_img.setVisibility(View.INVISIBLE);
            } else {//다른사람이면
                vh.view_onimg.setVisibility(View.VISIBLE);
                items.get(i - 1).setPrev(false);
                vh.pro_img.setVisibility(View.VISIBLE);
            }
        } else {//첫번쨰 항목이면
            items.get(i).setPrev(false);
            vh.view_onimg.setVisibility(View.VISIBLE);
            vh.pro_img.setVisibility(View.VISIBLE);
        }


        if (chat.getSender().equals(myname)) { //내가 보냈나?
            vh.pro_img.setVisibility(View.GONE);
            vh.view_onimg.setVisibility(View.GONE);
            vh.LL.setGravity(Gravity.RIGHT);//오른쪽에 붙임
        } else {
            vh.LL.setGravity(Gravity.LEFT);//왼쪽에 붙임
        }
//        if (items.size() > 1) {
//            if (items.get(items.size() - 1).getDate().equals(items.get(items.size() - 2).getDate())
//                    && items.get(items.size() - 1).getTimestamp().equals(items.get(items.size() - 2).getTimestamp())
//                    && items.get(items.size() - 1).getSender().equals(items.get(items.size() - 2).getSender())) {//같은사람, 같은 시간, 같은 날짜
//            }
//        }


        vh.message.setText(chat.getMessage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}