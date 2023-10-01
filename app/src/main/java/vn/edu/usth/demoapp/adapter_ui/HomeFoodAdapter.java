package vn.edu.usth.demoapp.adapter_ui;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

import vn.edu.usth.demoapp.activity_ui.MainActivity;
import vn.edu.usth.demoapp.activity_ui.SingleFoodActivity;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

public class HomeFoodAdapter extends RecyclerView.Adapter<HomeFoodAdapter.HomeFoodViewHolder> {

    public Context mContext;
    public List<Food> mListFood;

    private ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.loadingPanel);

        return new HomeFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null){
            return;
        }

        holder.tvName.setText(food.getName());

        loadImageUsingVolley(food.getUrlImage(), holder.imgFood);

        holder.itemView.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("food_name", food.getName());
            b.putString("food_url", food.getUrlImage());
            b.putFloat("food_rate", food.getStar());
            b.putString("food_description", food.getDescription());
            b.putString("type", "recipe_item");
            Intent intent = new Intent(mContext, SingleFoodActivity.class);
            intent.putExtras(b);
            mContext.startActivity(intent);
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

    public void loadImageUsingVolley(String url, ImageView imageView) {
        RequestQueue queue = ((MainActivity) mContext).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            imageView.setImageBitmap(response);
            progressBar.setVisibility(View.GONE);
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(mContext, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
    }
}
