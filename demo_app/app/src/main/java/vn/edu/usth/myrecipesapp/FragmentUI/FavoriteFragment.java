package vn.edu.usth.myrecipesapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.usth.myrecipesapp.R;

public class FavoriteFragment extends Fragment {

    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        mView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        return mView;
    }
}