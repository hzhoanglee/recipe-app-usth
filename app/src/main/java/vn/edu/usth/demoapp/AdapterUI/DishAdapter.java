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

import vn.edu.usth.demoapp.ObjectUI.Dish;
import vn.edu.usth.demoapp.R;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    public Context mContext;
    public List<Dish> mListDish;

    public DishAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Dish> list){
        this.mListDish = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = mListDish.get(position);
        if (dish == null){
            return;
        }

        holder.imgDish.setImageResource(dish.getResourceId());
        holder.nameDish.setText(dish.getName());
    }

    @Override
    public int getItemCount() {
        if (mListDish != null){
            return mListDish.size();
        }
        return 0;
    }

    public class DishViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgDish;
        private TextView nameDish;
        public DishViewHolder(@NonNull View itemView) {
            super(itemView);

            imgDish = itemView.findViewById(R.id.img_category);
            nameDish = itemView.findViewById(R.id.name_category);
        }
    }

}
