package vn.edu.usth.demoapp.fragment_ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.edu.usth.demoapp.adapter_ui.FoodAdapter;
import vn.edu.usth.demoapp.interface_controller.StatusCallback;
import vn.edu.usth.demoapp.interface_controller.UserCallback;
import vn.edu.usth.demoapp.network_controller.UserController;
import vn.edu.usth.demoapp.object_ui.Food;
import vn.edu.usth.demoapp.R;

public class FavoriteFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FoodAdapter foodAdapter;
        RecyclerView rcvFood;
        View mView;

        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            mView = inflater.inflate(R.layout.fragment_favorite_login, container, false);
            Button loginButton = mView.findViewById(R.id.buttonLogin);
            loginButton.setOnClickListener(v -> {
                EditText editUsername = mView.findViewById(R.id.editTextUsername);
                EditText editPassword = mView.findViewById(R.id.editTextPassword);

                UserController userController = new UserController();
                userController.userLogin(editUsername.getText().toString(), editPassword.getText().toString(), requireContext(), new StatusCallback() {
                    @Override
                    public void onStatusOK(boolean success) {
                        Intent intent = requireActivity().getIntent();
                        requireActivity().finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });

            });

            TextView createAccount = mView.findViewById(R.id.textViewCreateAccount);
            createAccount.setOnClickListener(v -> openRegisterDialog(Gravity.CENTER, "create_account"));


        } else {
            mView = inflater.inflate(R.layout.fragment_explore, container, false);
            rcvFood = mView.findViewById(R.id.rcv_food);
            foodAdapter = new FoodAdapter(requireContext());

            GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
            rcvFood.setLayoutManager(gridLayoutManager);
            //List<Food> list = getListFood();

            foodAdapter.setData(getListFood());
            rcvFood.setAdapter(foodAdapter);

            return mView;
        }


        return mView;
    }

    private void openRegisterDialog(int gravity, String type) {
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

        buttonCancel.setOnClickListener(v -> dialog.dismiss());

        if(type.equals("create_account")) {
            // handle buttons for search dialog
            EditText regUsername = dialog.findViewById(R.id.edit_username);
            EditText regPassword = dialog.findViewById(R.id.edit_password);
            EditText regRePassword = dialog.findViewById(R.id.edit_re_password);
            EditText regEmail = dialog.findViewById(R.id.edit_email);
            buttonSearch.setOnClickListener(v -> {
                if(!regPassword.getText().toString().equals(regRePassword.getText().toString())) {
                    Toast.makeText(requireContext(), "Error: Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    UserController userRegister = new UserController();
                    userRegister.userRegister(regUsername.getText().toString(), regPassword.getText().toString(),regEmail.getText().toString(), requireContext(), new StatusCallback() {
                        @Override
                        public void onStatusOK(boolean status) {
                            dialog.dismiss();
                            Toast.makeText(requireContext(), "Register success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                EditText editTextUsername = requireActivity().findViewById(R.id.editTextUsername);
                editTextUsername.setText(regEmail.getText().toString());
                EditText editTextPassword = requireActivity().findViewById(R.id.editTextPassword);
                editTextPassword.setText(regPassword.getText().toString());


            });

        } else {
            Toast.makeText(requireContext(), "Error: Invalid dialog type", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

        dialog.show();

    }
    private List<Food> getListFood(){

        List<Food> list = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * list.size());
            Food temp = list.get(randomIndexToSwap);
            list.set(randomIndexToSwap, list.get(i));
            list.set(i, temp);
        }

        return list;
    }




}