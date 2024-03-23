package com.example.user.reservation.announcement_folder;

/**
 * Created by User on 07-Dec-17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.reservation.R;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */

public class announcement_adapter extends RecyclerView.Adapter<announcement_adapter.ProductViewHolder> {


    private Context mCtx;
    private List<announcement_content> announcementList;


    public announcement_adapter(Context mCtx, List<announcement_content> announcementList) {
        this.mCtx = mCtx;
        this.announcementList = announcementList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.announcement, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        final announcement_content abc = announcementList.get(position);


        holder.textViewDate.setText(": "+abc.getannouncement_date());
        holder.textViewTitle.setText(": "+abc.getannouncement_title());
        holder.textViewDescription.setText(": "+String.valueOf(abc.getannouncement_description()));


    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDate, textViewTitle, textViewDescription;


        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.date);
            textViewTitle = (TextView) itemView.findViewById(R.id.title);
            textViewDescription = (TextView) itemView.findViewById(R.id.description);


        }
    }
}
