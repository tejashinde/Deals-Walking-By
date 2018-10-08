
package com.example.tejas.dealswalkingby;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ProductViewHolder> {


    private Context mContext;
    private List<MessageStructure> mMessageList;

    interface OnItemClicked {
        public void OnItemClickedHere(MessageStructure MessageStructure);
    }

    public ChatAdapter(Context context, List<MessageStructure> MessageList) {
        mContext = context;
        mMessageList = MessageList;
    }


//    private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.deal_row_structure, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final com.example.tejas.dealswalkingby.MessageStructure mMessageStructure = mMessageList.get(position);

        holder.message.setText(mMessageStructure.getContent());
    }


    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView message;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.chat_message_text);
        }

    }

}
