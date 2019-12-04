package com.example.producehero.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.producehero.R;
import com.example.producehero.model.entity.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic RecyclerView adapter class to show Restaurants
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    //Need to be initialized before we get first LiveData update
    private List<Restaurant> restaurantList = new ArrayList<>();

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.restaurantNameView.setText(restaurant.getName());
        holder.restaurantAddressView.setText(restaurant.getAddress());
        //Only show the signed indicator if the Restaurant has a signature file on the device
        if (restaurant.getSignatureFile().isEmpty()) {
            holder.signedButton.setVisibility(View.INVISIBLE);
        } else {
            holder.signedButton.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        //Update all the cells of the RecyclerView on a change, can be improved
        notifyDataSetChanged();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantNameView;
        TextView restaurantAddressView;
        Button signedButton;
        Button mapButton;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameView = itemView.findViewById(R.id.restaurant_name_view);
            restaurantAddressView = itemView.findViewById(R.id.restaurant_address_view);

            signedButton = itemView.findViewById(R.id.signed_button);
            signedButton.setEnabled(false);
            mapButton = itemView.findViewById(R.id.map_button);

            //Click listeners for both the item itself and the map button_positive
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                        itemClickListener.onItemClick(restaurantList.get(position));
                    }
                }
            });
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mapButtonClickListener != null && position != RecyclerView.NO_POSITION) {
                        mapButtonClickListener.onMapButtonClick(restaurantList.get(position));
                    }
                }
            });
        }
    }

    //Click listeners
    private OnItemClickListener itemClickListener;
    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }
    private OnMapButtonClickListener mapButtonClickListener;
    public interface OnMapButtonClickListener {
        void onMapButtonClick(Restaurant restaurant);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public void setMapButtonClickListener(OnMapButtonClickListener listener) {
        this.mapButtonClickListener = listener;
    }
}
