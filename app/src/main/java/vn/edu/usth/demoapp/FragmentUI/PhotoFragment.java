package vn.edu.usth.demoapp.FragmentUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.usth.demoapp.ActivityUI.SingleFoodActivity;
import vn.edu.usth.demoapp.ObjectUI.Photo;
import vn.edu.usth.demoapp.R;

public class PhotoFragment extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_photo, container, false);
        Bundle bundle = getArguments();
        Photo photo =(Photo) bundle.get("objPhoto");

        ImageView imgPhoto = mView.findViewById(R.id.imgPhoto);
        imgPhoto.setImageResource(photo.getImgResourceID());
        TextView imgText = mView.findViewById(R.id.imgText);
        imgText.setText(photo.getTextResource());

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("food_name", photo.getTextResource());
                b.putInt("food_img", photo.getImgResourceID());
                b.putFloat("food_rate", 4.5F);
                b.putString("food_description", "Description alo alo");
                b.putString("type", "recipe_item");
                Intent intent = new Intent(requireContext(), SingleFoodActivity.class);
                intent.putExtras(b);
                requireContext().startActivity(intent);
            }
        });

        return mView;
    }
}