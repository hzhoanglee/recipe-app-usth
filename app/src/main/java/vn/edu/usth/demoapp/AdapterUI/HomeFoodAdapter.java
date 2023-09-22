package vn.edu.usth.demoapp.AdapterUI;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.demoapp.ActivityUI.SingleFoodActivity;
import vn.edu.usth.demoapp.ObjectUI.Food;
import vn.edu.usth.demoapp.R;

public class HomeFoodAdapter extends RecyclerView.Adapter<HomeFoodAdapter.HomeFoodViewHolder> {

    public Context mContext;
    public List<Food> mListFood;

    public HomeFoodAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public void setData(List<Food> list){
        this.mListFood = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item_recent, parent, false);

        return new HomeFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null){
            return;
        }
        holder.imgFood.setImageResource(food.getResourceImage());
        holder.tvName.setText(food.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("food_name", food.getName());
                b.putInt("food_img", food.getResourceImage());
                b.putFloat("food_rate", food.getStar());
                b.putString("food_description", food.getDescription());
                b.putString("type", "recipe_item");
                Intent intent = new Intent(mContext, SingleFoodActivity.class);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mListFood !=null){
            return mListFood.size();
        }
        return 0;
    }

    public class HomeFoodViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFood;
        private TextView tvName;

        public HomeFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_user_home_recent);
            tvName = itemView.findViewById(R.id.tv_home_recent_name);
        }
    }
}
