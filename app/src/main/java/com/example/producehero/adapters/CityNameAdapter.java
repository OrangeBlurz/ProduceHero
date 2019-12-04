package com.example.producehero.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.producehero.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic RecyclerView adapter class to show city names
 */
public class CityNameAdapter extends RecyclerView.Adapter<CityNameAdapter.CityNameHolder> {
    //Need to be initialized before we get first LiveData update
    private List<String> cityNameList = new ArrayList<>();

    @NonNull
    @Override
    public CityNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_item, parent, false);
        return new CityNameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityNameHolder holder, int position) {
        String cityName = cityNameList.get(position);
        holder.cityNameView.setText(cityName);
    }

    @Override
    public int getItemCount() {
        return cityNameList.size();
    }

    public void setCityNameList(List<String> cityNameList) {
        this.cityNameList = cityNameList;
        //Update all the cells of the RecyclerView on a change, can be improved
        notifyDataSetChanged();
    }

    public class CityNameHolder extends RecyclerView.ViewHolder {
        private TextView cityNameView;

        public CityNameHolder(@NonNull View itemView) {
            super(itemView);
            cityNameView = itemView.findViewById(R.id.city_name_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(cityNameList.get(position));
                    }
                }
            });
        }
    }

    //Click listeners
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(String cityName);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
