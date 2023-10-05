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

import vn.edu.usth.demoapp.activity_ui.SingleFoodActivity;
import vn.edu.usth.demoapp.object_ui.Photo;
import vn.edu.usth.demoapp.R;

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
        Photo photo =(Photo) bundle.get("objPhoto");

        mContext = requireContext();

        assert photo != null;
        loadImageUsingVolley(photo.getUrlImage(), CarouselPhotoView.findViewById(R.id.imgPhoto));

        ImageView imgPhoto = CarouselPhotoView.findViewById(R.id.imgPhoto);
        TextView imgText = CarouselPhotoView.findViewById(R.id.imgText);
        imgText.setText(photo.getName());

        imgPhoto.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("food_name", photo.getName());
            b.putString("food_url", photo.getUrlImage());
            b.putFloat("food_rate", photo.getStar());
            b.putString("food_description", photo.getDescription());
            b.putString("type", "recipe_item");
            Intent intent = new Intent(mContext, SingleFoodActivity.class);
            intent.putExtras(b);
            mContext.startActivity(intent);
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
}