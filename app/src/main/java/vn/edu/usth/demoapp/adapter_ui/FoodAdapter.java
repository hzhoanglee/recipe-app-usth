package vn.edu.usth.demoapp.adapter_ui;


import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.activity_ui.SingleFoodActivity;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.object_ui.RecentList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public Context mContext;
    public List<Food> mListFood;

    private ProgressBar progressBar;


    public FoodAdapter(Context mContext) {

        this.mContext = mContext;

        RecentList.getRecentlyClickedItemsLiveData().observeForever(new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                notifyDataSetChanged();
            }
        });
    }

    public void setData(List<Food> list){
        this.mListFood = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_explore_food, parent, false);
        progressBar = view.findViewById(R.id.loadingPanel);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null){
            return;
        }
        holder.tvName.setText(food.getName());
        holder.ratingBar.setRating(food.getStar());
        holder.tv_description.setText(food.getDescription());
        holder.tv_prepare_time.setText(food.getPrepTime());
        holder.tv_cook_time.setText(food.getCookTime());
        holder.tv_level.setText(food.getLevel());

        loadImageUsingVolley(food.getUrlImage(), holder.imgFood);

        holder.itemView.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("food_name", food.getName());
            b.putString("food_url", food.getUrlImage());
            b.putFloat("food_rate", food.getStar());
            b.putString("food_description", food.getDescription());
            b.putString("food_html", food.getHtmlContent());
            b.putString("type", "recipe_item");
            b.putInt("food_id", food.getId());
            b.putString("food_category", food.getCategory());
            b.putString("food_prep_time", food.getPrepTime());
            b.putString("food_cook_time", food.getCookTime());
            b.putString("food_level", food.getLevel());
            b.putBoolean("food_favourite", food.isFavourite());
            Intent intent = new Intent(mContext, SingleFoodActivity.class);
            intent.putExtras(b);
            mContext.startActivity(intent);
            updateRecentlyClickedItems(food);
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

        ImageView imgFood;
        TextView tvName;
        RatingBar ratingBar;
        TextView tv_description;

        TextView tv_prepare_time;
        TextView tv_cook_time;
        TextView tv_level;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_user);
            tvName = itemView.findViewById(R.id.tv_name);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_prepare_time = itemView.findViewById(R.id.tv_prepare_time);
            tv_cook_time = itemView.findViewById(R.id.tv_cook_time);
            tv_level = itemView.findViewById(R.id.tv_level);
        }
    }

    public void loadImageUsingVolley(String url, ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            imageView.setImageBitmap(response);
            progressBar.setVisibility(View.GONE);
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(mContext, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
    }

    @Override
    public long getItemId(int position) {
        return mListFood.get(position).getId();
    }

    /**
     * Update recently clicked items
     * @param food
     * if the item is already in the list, move it to the top
     * if the item is not in the list, add it to the top
     * if the list has more than 10 items, remove the last item
     * if the list is empty, add the item to the list unconditionally
     */
    public void updateRecentlyClickedItems(Food food) {
        List<Food> currentList = RecentList.getRecentlyClickedItemsLiveData().getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        if (currentList.contains(food)) {
            currentList.remove(food);
        }
        currentList.add(0, food);
        if (currentList.size() > 10) {
            currentList.remove(currentList.size() - 1);
        }
        RecentList.getRecentlyClickedItemsLiveData().setValue(currentList);
    }

}
