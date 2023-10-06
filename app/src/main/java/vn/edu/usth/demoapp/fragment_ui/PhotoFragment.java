package vn.edu.usth.demoapp.fragment_ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.demoapp.activity_ui.SingleFoodActivity;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.object_ui.Photo;
import vn.edu.usth.demoapp.R;
import vn.edu.usth.demoapp.object_ui.RecentList;

public class PhotoFragment extends Fragment {

    private View CarouselPhotoView;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CarouselPhotoView =  inflater.inflate(R.layout.fragment_photo, container, false);
        Bundle bundle = getArguments();
        Food photo =(Food) bundle.get("objFood");

        mContext = requireContext();

        assert photo != null;
        loadImageUsingVolley(photo.getUrlImage(), CarouselPhotoView.findViewById(R.id.imgPhoto));

        ImageView imgPhoto = CarouselPhotoView.findViewById(R.id.imgPhoto);
        TextView imgText = CarouselPhotoView.findViewById(R.id.imgText);
        TextView imgCategory = CarouselPhotoView.findViewById(R.id.ViewCategory);
        TextView imgTime = CarouselPhotoView.findViewById(R.id.ViewPrepTime);
        imgText.setText(photo.getName());
        imgCategory.setText(photo.getCategory());
        imgTime.setText(photo.getPrepTime());

        imgPhoto.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("food_name", photo.getName());
            b.putString("food_url", photo.getUrlImage());
            b.putFloat("food_rate", photo.getStar());
            b.putString("food_description", photo.getDescription());
            b.putString("food_html", photo.getHtmlContent());
            b.putString("type", "recipe_item");
            b.putInt("food_id", photo.getId());
            b.putString("food_category", photo.getCategory());
            b.putString("food_prep_time", photo.getPrepTime());
            b.putString("food_cook_time", photo.getCookTime());
            b.putString("food_level", photo.getLevel());
            b.putBoolean("food_favourite", photo.isFavourite());
            Intent intent = new Intent(mContext, SingleFoodActivity.class);
            intent.putExtras(b);
            mContext.startActivity(intent);
            updateRecentlyClickedItems(photo);
        });

        return CarouselPhotoView;
    }

    /**
     * load image using volley
     * loading image from url
     * @param url : [String]
     * @param imageView
     */
    public void loadImageUsingVolley(String url, ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        ImageRequest imageRequest = new ImageRequest(url, response -> {
            imageView.setImageBitmap(response);
            CarouselPhotoView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, error -> {
            Toast.makeText(mContext, "Error while loading image", Toast.LENGTH_SHORT).show();
            error.printStackTrace();
        });
        queue.add(imageRequest);
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