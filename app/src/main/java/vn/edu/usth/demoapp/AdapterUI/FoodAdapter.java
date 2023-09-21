package vn.edu.usth.demoapp.AdapterUI;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.demoapp.ObjectUI.Food;
import vn.edu.usth.demoapp.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public Context mContext;
    public List<Food> mListFood;

    public FoodAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public void setData(List<Food> list){
        this.mListFood = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_explore_food, parent, false);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null){
            return;
        }
        holder.imgFood.setImageResource(food.getResourceImage());
        holder.tvName.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        if(mListFood !=null){
            return mListFood.size();
        }
        return 0;
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFood;
        private TextView tvName;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            // Corrected lines to find views by calling findViewById on itemView
            imgFood = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
