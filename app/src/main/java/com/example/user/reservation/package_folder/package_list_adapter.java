package com.example.user.reservation.package_folder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.reservation.R;

import java.util.List;

/**
 * Created by User on 09-Dec-17.
 */

public class package_list_adapter extends RecyclerView.Adapter<package_list_adapter.ProductViewHolder> {


    private Context mCtx;
    private List<package_content> productList;


    public package_list_adapter(Context mCtx, List<package_content> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public package_list_adapter.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.package_content, null);
        return new package_list_adapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final package_list_adapter.ProductViewHolder holder, int position) {
        final package_content product = productList.get(position);


        holder.textViewName.setText(": "+product.getPackage_name());
        holder.textViewPrice.setText(": "+String.valueOf(product.getPackage_price()));
        holder.textViewDescription.setText(": "+product.getPackage_description());
        holder.textViewDay.setText(": "+String.valueOf(product.getPackage_day()));
        holder.textViewAddress.setText(": "+product.getPackage_address());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewPrice, textViewDescription,textViewDay,textViewAddress;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewName= (TextView) itemView.findViewById(R.id.name);
            textViewPrice = (TextView) itemView.findViewById(R.id.price);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);
            textViewDay = (TextView) itemView.findViewById(R.id.day);
            textViewAddress = (TextView) itemView.findViewById(R.id.address);
        }
    }
}
