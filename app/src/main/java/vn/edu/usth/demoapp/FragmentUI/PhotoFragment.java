package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        return mView;
    }
}