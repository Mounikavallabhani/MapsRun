package com.strsar.mapsrun.Activitys;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strsar.mapsrun.R;

import java.util.ArrayList;

public class DeliveryBoyAdapter  extends RecyclerView.Adapter<DeliveryBoyAdapter.Myview> {
    Context context;
    ArrayList<DeliveryBoyModel>deliveryBoyModels;
    View view;

    public DeliveryBoyAdapter(Context context, ArrayList<DeliveryBoyModel> deliveryBoyModels) {
        this.context = context;
        this.deliveryBoyModels = deliveryBoyModels;
    }

    @NonNull
    @Override
    public Myview onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        view =layoutInflater.inflate(R.layout.deliveryboyorders,viewGroup,false);
        return new Myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myview myview, int i) {
        DeliveryBoyModel deliveryBoyModel=deliveryBoyModels.get(i);
        myview.deliveryboy_id.setText(deliveryBoyModel.getBook_id());
        myview.user_address.setText(deliveryBoyModel.getUsers());
        myview.seller_address.setText(deliveryBoyModel.getSellers());

    }

    @Override
    public int getItemCount() {
        return deliveryBoyModels.size();
    }

    public class Myview extends RecyclerView.ViewHolder {
        TextView deliveryboy_id,user_address,seller_address;
        public Myview(@NonNull View itemView) {
            super(itemView);
            deliveryboy_id=itemView.findViewById(R.id.deliveryboy_id);
            user_address=itemView.findViewById(R.id.user_address);
            seller_address=itemView.findViewById(R.id.seller_address);

        }
    }
}
