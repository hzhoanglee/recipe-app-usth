package vn.edu.usth.myrecipesapp.FragmentUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.widget.Switch;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import vn.edu.usth.myrecipesapp.FoodRecipe.Steak;
import vn.edu.usth.myrecipesapp.FoodRecipe.Steak1;
import vn.edu.usth.myrecipesapp.FoodRecipe.Steak2;
import vn.edu.usth.myrecipesapp.R;
import vn.edu.usth.demoapp.R;

public class HomeFragment extends Fragment implements View.OnClickListener{

    CarouselView carouselView;

    int images[] = {R.drawable.steak, R.drawable.steak1, R.drawable.steak2};
    private View mView;
    private Switch mSwitch;

    private CardView steak;
    private CardView macCheese;
    private CardView breakfast;
    public HomeFragment(){

    }/**/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundLight));

        // Cardview
        steak = (CardView) mView.findViewById(R.id.steak);
        steak.setOnClickListener((View.OnClickListener)this);

        macCheese = (CardView) mView.findViewById(R.id.macCheese);
        macCheese.setOnClickListener((View.OnClickListener)this);

        breakfast = (CardView) mView.findViewById(R.id.breakfast);
        breakfast.setOnClickListener((View.OnClickListener)this);


        // carousel
        carouselView = (CarouselView) mView.findViewById(R.id.carouselView);
        carouselView.setPageCount(images.length);

        carouselView.setImageListener(imageListener);
        //edit R.layout.toolbar.title_toolbar text to Home

        return mView;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(images[position]);
            int IPosition = position;
        }
    };


    @Override
    public void onClick(View v){
        Intent mIntent;
        switch (v.getId()){
            case R.id.steak : mIntent = new Intent(getActivity(), Steak.class); startActivity(mIntent); break;
            case R.id.macCheese: mIntent = new Intent(getActivity(), Steak1.class); startActivity(mIntent); break;
            case R.id.breakfast: mIntent = new Intent(getActivity(), Steak2.class); startActivity(mIntent); break;
        }

    }
}