package com.example.user.reservation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 27-Dec-17.
 */

public class rating_adapter extends RecyclerView.Adapter<rating_adapter.ProductViewHolder> {


    private Context mCtx;
    private List<rating_content> ratingList;


    public rating_adapter(Context mCtx, List<rating_content> productList) {
        this.mCtx = mCtx;
        this.ratingList = productList;
    }

    @Override
    public rating_adapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.rating2, null);
        return new rating_adapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final rating_adapter.ProductViewHolder holder, int position) {
        final rating_content product = ratingList.get(position);


       // holder.textViewRating.setText(": "+product.getRating());
        holder.textViewComment.setText(product.getComment());
        float rating = Float.parseFloat(product.getRating());
        holder.ratingBar.setRating(rating);

    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRating, textViewComment;
        RatingBar ratingBar;

        public ProductViewHolder(View itemView) {
            super(itemView);
           // textViewRating= (TextView) itemView.findViewById(R.id.rating);
            textViewComment = (TextView) itemView.findViewById(R.id.comment);
             ratingBar = (RatingBar) itemView.findViewById(R.id.pop_ratingbar);

        }
    }
}
