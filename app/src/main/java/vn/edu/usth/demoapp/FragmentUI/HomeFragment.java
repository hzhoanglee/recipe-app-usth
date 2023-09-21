package vn.edu.usth.demoapp.FragmentUI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import vn.edu.usth.demoapp.R;

public class HomeFragment extends Fragment {

    private View mView;
    private Switch mSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        //edit R.layout.toolbar.title_toolbar text to Home

        return mView;
    }
}