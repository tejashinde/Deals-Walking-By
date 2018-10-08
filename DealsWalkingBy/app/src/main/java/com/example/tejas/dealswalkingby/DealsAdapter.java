
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


public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.ProductViewHolder> {


    private Context mContext;
    private List<com.example.tejas.dealswalkingby.DealStructure> mDealList;

    interface OnItemClicked {
        public void OnItemClickedHere(com.example.tejas.dealswalkingby.DealStructure dealStructure);
    }

    public DealsAdapter(Context context, List<com.example.tejas.dealswalkingby.DealStructure> dealList) {
        mContext = context;
        mDealList = dealList;
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
        final com.example.tejas.dealswalkingby.DealStructure mDealStructure = mDealList.get(position);

        holder.deal_title.setText(mDealStructure.getDeal_title());
        holder.deal_description.setText(mDealStructure.getDeal_description());
        holder.deal_validity.setText(mDealStructure.getDeal_validity());
        holder.deal_latitude.setText(mDealStructure.getDeal_latitude());
        holder.deal_longitude.setText(mDealStructure.getDeal_longitude());
        holder.deal_image_name.setText(mDealStructure.getDeal_image_name());
        holder.deal_validity_day.setText(mDealStructure.getDeal_validity_day());
        holder.deal_validity_month.setText(mDealStructure.getDeal_validity_month());
        holder.deal_validity_year.setText(mDealStructure.getDeal_validity_year());
        holder.deal_promo_code.setText(mDealStructure.getDeal_promo_code());
        Glide.with(mContext).load(mDealStructure.getDeal_image()).into(holder.mImageView);
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContext instanceof DealsActivity) {
                    ((OnItemClicked) mContext).OnItemClickedHere(mDealStructure);
                }
            }
        });

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
                Query deleteQuery = mDatabaseReference.child("Deals").orderByChild("deal_title").equalTo(mDealStructure.getDeal_title());

                deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.mPromoCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mPromoCodeButton.setVisibility(View.INVISIBLE);
                holder.deal_promo_code.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mDealList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView deal_image_name, deal_title, deal_description, deal_longitude, deal_latitude, deal_validity, deal_validity_day, deal_validity_month, deal_validity_year, deal_promo_code;
        ImageButton mImageButton;
        Button mDeleteButton;
        Button mPromoCodeButton;

        public ProductViewHolder(final View itemView) {
            super(itemView);
            mPromoCodeButton = itemView.findViewById(R.id.buttonGetPromo);
            deal_promo_code = itemView.findViewById(R.id.textViewPromoCode);
            deal_image_name = itemView.findViewById(R.id.deal_image_name);
            deal_title = itemView.findViewById(R.id.deal_title);
            deal_description = itemView.findViewById(R.id.deal_description);
            deal_longitude = itemView.findViewById(R.id.dealActivity_longitude);
            deal_latitude = itemView.findViewById(R.id.dealActivity_latitude);
            mImageView = itemView.findViewById(R.id.deal_image);
            deal_validity = itemView.findViewById(R.id.deal_validity);
            mImageButton = itemView.findViewById(R.id.imageButton2);
            mDeleteButton = itemView.findViewById(R.id.adminDeleteButton);
            deal_validity_day = itemView.findViewById(R.id.textViewDisplayDay);
            deal_validity_month = itemView.findViewById(R.id.textViewDisplayMonth);
            deal_validity_year = itemView.findViewById(R.id.textViewDisplayYear);
        }

    }

}
