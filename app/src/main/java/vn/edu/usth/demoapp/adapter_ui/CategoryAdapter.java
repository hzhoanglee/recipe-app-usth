package vn.edu.usth.demoapp.adapter_ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import vn.edu.usth.demoapp.activity_ui.SearchResultActivity;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    public Context mContext;
    public List<Category> mListCategory;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null){
            return;
        }

        holder.nameCategory.setText(category.getName());
//        Log.i("Category", category.getUrlImage());
        loadImageUsingVolley(category.getUrlImage(), holder.imgCategory);

        holder.imageButton.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("search_param", category.getName());
            b.putInt("category_id", category.getId());
            b.putString("type", "category");
            Intent intent = new Intent(mContext, SearchResultActivity.class);
            intent.putExtras(b);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView nameCategory;
        private View imageButton;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.img_category);
            nameCategory = itemView.findViewById(R.id.name_category);
            imageButton = itemView.findViewById(R.id.view_category);
        }


    }

    public void loadImageUsingVolley(String url, ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            imageView.setImageBitmap(response);
//            progressBar.setVisibility(View.GONE);
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(mContext, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
    }

}