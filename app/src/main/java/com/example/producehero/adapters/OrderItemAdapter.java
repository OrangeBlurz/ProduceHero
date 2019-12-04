package com.example.producehero.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producehero.R;
import com.example.producehero.model.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic RecyclerView adapter class to show Order Items
 */
public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {
    //Need to be initialized before we get first LiveData update
    private List<OrderItem> orderItemList = new ArrayList<>();

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item_list_item, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem item = orderItemList.get(position);
        holder.orderItemNameView.setText(item.getName());
        holder.orderItemWeightView.setText(item.getWeight() + "kg");
        holder.orderItemQuantityView.setText("x" + item.getQuantity());
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
        //Update all the cells of the RecyclerView on a change, can be improved
        notifyDataSetChanged();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{
        TextView orderItemNameView;
        TextView orderItemQuantityView;
        TextView orderItemWeightView;
        Button modifyButton;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderItemNameView = itemView.findViewById(R.id.item_name_view);
            orderItemQuantityView = itemView.findViewById(R.id.item_quantity_view);
            orderItemWeightView = itemView.findViewById(R.id.item_weight_view);
            modifyButton = itemView.findViewById(R.id.modify_item_button);

            modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onButtonClick(orderItemList.get(position));
                    }
                }
            });
        }
    }

    //Click listener
    private OnButtonClickListener listener;
    public interface OnButtonClickListener{
        void onButtonClick(OrderItem item);
    }
    public void setButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }
}
