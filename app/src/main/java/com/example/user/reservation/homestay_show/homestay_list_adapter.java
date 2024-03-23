package com.example.user.reservation.homestay_show;

/**
 * Created by User on 01-Dec-17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.reservation.R;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class homestay_list_adapter extends RecyclerView.Adapter<homestay_list_adapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;


    public homestay_list_adapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.homestay_list_content, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final Product product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImage_name())
                .into(holder.imageView);

        holder.textViewTitle.setText(product.getHomestay_address());
        holder.textViewShortDesc.setText(product.getHomestay_name());
        holder.textViewRating.setText(String.valueOf(product.getHomestay_capacity()) + " (max people)");
        holder.textViewPrice.setText("RM "+String.valueOf(product.getHomestay_price_perday()));

        holder.textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx,make_booking.class);
                intent.putExtra("homestay_id",String.valueOf(product.getId()));
                intent.putExtra("homestay_name",product.getHomestay_name());
                intent.putExtra("homestay_price_perday",String.valueOf(product.getHomestay_price_perday()));
                intent.putExtra("homestay_address",product.getHomestay_address());
                intent.putExtra("homestay_postcode",String.valueOf(product.getHomestay_postcode()));
                intent.putExtra("homestay_state",product.getHomestay_state());
                intent.putExtra("homestay_city",product.getHomestay_city());
                intent.putExtra("homestay_capacity",String.valueOf(product.getHomestay_capacity()));
                intent.putExtra("homestay_room",String.valueOf(product.getHomestay_room()));
                intent.putExtra("homestay_status",product.getHomestay_status());
                intent.putExtra("homestay_description",product.getHomestay_description());

                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = (TextView) itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = (TextView) itemView.findViewById(R.id.textViewRating);
            textViewPrice = (TextView) itemView.findViewById(R.id.textViewPrice);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);


        }
    }
}