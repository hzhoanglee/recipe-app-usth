package vn.edu.usth.demoapp.AdapterUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.usth.demoapp.ActivityUI.MainActivity;
import vn.edu.usth.demoapp.ActivityUI.SearchResultActivity;
import vn.edu.usth.demoapp.ObjectUI.Category;
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

        holder.imgCategory.setImageResource(category.getResourceId());
        holder.nameCategory.setText(category.getName());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("search_param", category.getName());
                b.putString("type", "category");
                Intent intent = new Intent(mContext, SearchResultActivity.class);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgCategory;
        private TextView nameCategory;
        private View imageButton;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCategory = itemView.findViewById(R.id.img_category);
            nameCategory = itemView.findViewById(R.id.name_category);
            imageButton = itemView.findViewById(R.id.view_category);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), "Gay = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}