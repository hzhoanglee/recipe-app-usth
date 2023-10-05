package vn.edu.usth.demoapp.adapter_ui;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static vn.edu.usth.demoapp.network_controller.Helpers.loadImageUsingVolley;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.demoapp.activity_ui.SearchResultActivity;
import vn.edu.usth.demoapp.object_ui.Category;
import vn.edu.usth.demoapp.R;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {

    public Context mContext;
    public List<Category> mListCategory;

    public HomeCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list){
        this.mListCategory = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_item_category, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if (category == null){
            return;
        }

        loadImageUsingVolley(mContext, category.getUrlImage(), holder.imgCategory);
        holder.nameCategory.setText(category.getName());

        holder.imageButton.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("search_param", category.getName());
            b.putString("type", "category");
            b.putInt("category_id", category.getId());
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

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCategory;
        private TextView nameCategory;
        private View imageButton;
        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.img_category_home);
            nameCategory = itemView.findViewById(R.id.name_category_home);
            imageButton = itemView.findViewById(R.id.view_category_home);
        }


    }

}