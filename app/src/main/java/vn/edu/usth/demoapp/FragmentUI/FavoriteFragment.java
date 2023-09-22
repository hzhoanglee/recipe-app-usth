package vn.edu.usth.demoapp.FragmentUI;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.usth.demoapp.ActivityUI.MainActivity;
import vn.edu.usth.demoapp.ActivityUI.SearchResultActivity;
import vn.edu.usth.demoapp.AdapterUI.FoodAdapter;
import vn.edu.usth.demoapp.ObjectUI.Food;
import vn.edu.usth.demoapp.R;

public class FavoriteFragment extends Fragment {

    private View mView;
    private RecyclerView rcvFood;
    private FoodAdapter foodAdapter;
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

                    // reload app
                    Intent intent = requireActivity().getIntent();
                    requireActivity().finish();
                    startActivity(intent);

                }
            });

            TextView createAccount = mView.findViewById(R.id.textViewCreateAccount);
            createAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSearchDialog(Gravity.CENTER, "create_account");
                }
            });


        } else {
            mView = inflater.inflate(R.layout.fragment_explore, container, false);
            rcvFood = mView.findViewById(R.id.rcv_food);
            foodAdapter = new FoodAdapter(requireContext());

            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
            rcvFood.setLayoutManager(gridLayoutManager);
            List<Food> list = getListFood();

            foodAdapter.setData(getListFood());
            rcvFood.setAdapter(foodAdapter);

            return mView;
        }


        return mView;
    }

    private void openSearchDialog(int gravity, String type) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(type.equals("create_account"))
            dialog.setContentView(R.layout.layout_dialog_register);
        else {
            Toast.makeText(requireContext(), "Error: Invalid dialog type", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(Gravity.CENTER == gravity);

        Button buttonCancel = dialog.findViewById(R.id.buttonCancel);
        Button buttonSearch = dialog.findViewById(R.id.buttonSearch);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(type.equals("create_account")) {
            // handle buttons for search dialog
            EditText editUsername = dialog.findViewById(R.id.edit_username);
            EditText editPassword = dialog.findViewById(R.id.edit_password);
            EditText editRePassword = dialog.findViewById(R.id.edit_re_password);
            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editPassword.getText().toString().equals(editRePassword.getText().toString())) {
                        Toast.makeText(requireContext(), "Error: Passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(requireContext(), "Welcome: " + editUsername.getText().toString(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("myKey", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = requireActivity().getIntent();
                    requireActivity().finish();
                    startActivity(intent);
                }
            });

        } else {
            Toast.makeText(requireContext(), "Error: Invalid dialog type", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        dialog.show();

    }
    private List<Food> getListFood(){
        List<Food> list = new ArrayList<>();
        list.add(new Food(R.drawable.appetizers, "Appetizers", randomStar(), "This is appetizers recipe that you can make at home"));
        list.add(new Food(R.drawable.breakfast, "Breakfast", randomStar(), "This is breakfast recipe that you can make at home"));

        for (int i = 0; i < list.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * list.size());
            Food temp = list.get(randomIndexToSwap);
            list.set(randomIndexToSwap, list.get(i));
            list.set(i, temp);
        }

        return list;
    }

    private float randomStar(){
        return (float) (Math.random() * 2 + 3);
    }
}