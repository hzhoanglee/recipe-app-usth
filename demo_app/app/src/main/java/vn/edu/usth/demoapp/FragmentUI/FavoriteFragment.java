package vn.edu.usth.demoapp.FragmentUI;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import vn.edu.usth.demoapp.R;

public class FavoriteFragment extends Fragment {

    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            // get shared preferences
            mView = inflater.inflate(R.layout.fragment_favorite_login, container, false);

            // get button
            Button loginButton = mView.findViewById(R.id.buttonLogin);

            // set on click listener
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(requireActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();

                }
            });

        } else {
            Toast.makeText(requireActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        }


        return mView;
    }
}