package com.example.user.reservation;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 14-Dec-17.
 */


class BookingHistory_apdater extends RecyclerView.Adapter<BookingHistory_apdater.ProductViewHolder> {


    private Context mCtx;
    private List<BookingHistory_content> historyList;


    public BookingHistory_apdater(Context mCtx, List<BookingHistory_content> announcementList) {
        this.mCtx = mCtx;
        this.historyList = announcementList;
    }

    @Override
    public BookingHistory_apdater.ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.bookinghistory2, null);
        return new BookingHistory_apdater.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookingHistory_apdater.ProductViewHolder holder, int position) {
        final BookingHistory_content abc = historyList.get(position);


        holder.textViewStatus.setText(": "+abc.getStatus());
        holder.textViewPrice.setText(": RM "+String.valueOf(abc.getTotal_payment()));
        holder.textViewHomestay_name.setText(": "+abc.getName());

        //change the date format//
        StringBuilder str_checkin = new StringBuilder(abc.getCheckin());
        str_checkin.insert(4, '/');
        str_checkin.insert(7, '/');
        //change the date format//

        //change the date format//
        StringBuilder str_checkout = new StringBuilder(abc.getCheckout());
        str_checkout.insert(4, '/');
        str_checkout.insert(7, '/');
        //change the date format//

        holder.textViewCheckin.setText(" : "+str_checkin.toString());
        holder.textViewCheckout.setText(" : "+str_checkout.toString());
        holder.textViewAddress.setText(": "+abc.getAddress());
        holder.textViewReservationNo.setText("No: "+String.valueOf(position+1));


            holder.buttonPay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(mCtx, booking_details.class);
                    intent.putExtra("reservation_id", String.valueOf(abc.getReservation_id()));
                    intent.putExtra("homestay_name", abc.getName());
                    intent.putExtra("status", abc.getStatus());
                    intent.putExtra("price", String.valueOf(abc.getTotal_payment()));
                    intent.putExtra("checkin", abc.getCheckin());
                    intent.putExtra("checkout", abc.getCheckout());
                    intent.putExtra("address", abc.getAddress());
                    intent.putExtra("receipt", abc.getReceipt());
                    mCtx.startActivity(intent);

                }
            });


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewStatus, textViewPrice, textViewHomestay_name,textViewCheckin, textViewCheckout,textViewAddress,textViewReservationNo;
        Button buttonPay;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewStatus = (TextView) itemView.findViewById(R.id.Status);
            textViewPrice = (TextView) itemView.findViewById(R.id.Price);
            textViewHomestay_name = (TextView) itemView.findViewById(R.id.Homestay_name);
            textViewCheckin = (TextView) itemView.findViewById(R.id.Checkin);
            textViewCheckout = (TextView) itemView.findViewById(R.id.Checkout);
            textViewAddress = (TextView) itemView.findViewById(R.id.Address);
            textViewReservationNo = (TextView) itemView.findViewById(R.id.ReservationNo);

             buttonPay = (Button) itemView.findViewById(R.id.buttonPay);

        }
    }
}
