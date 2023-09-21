package vn.edu.usth.demoapp.FragmentUI;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.Objects;

import vn.edu.usth.demoapp.ActivityUI.MainActivity;
import vn.edu.usth.demoapp.ActivityUI.SearchResultActivity;
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
            Toast.makeText(requireActivity(), "Logged in", Toast.LENGTH_SHORT).show();
            mView = inflater.inflate(R.layout.fragment_favorite, container, false);
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

        dialog.setCancelable(Gravity.BOTTOM == gravity);

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
}