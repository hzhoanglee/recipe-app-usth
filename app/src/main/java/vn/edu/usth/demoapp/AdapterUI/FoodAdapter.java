package vn.edu.usth.demoapp.AdapterUI;


import android.content.Context;
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
        holder.ratingBar.setRating(food.getStar());
        holder.tv_description.setText(food.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "You clicked " + food.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            Toast.makeText(mContext, "You rated " + food.getName() + " " + rating + " stars", Toast.LENGTH_SHORT).show();
        });

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
        private RatingBar ratingBar;
        private TextView tv_description;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tv_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }


}
