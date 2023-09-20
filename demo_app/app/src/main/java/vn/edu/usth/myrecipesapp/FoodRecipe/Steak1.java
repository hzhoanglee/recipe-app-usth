package vn.edu.usth.myrecipesapp.FoodRecipe;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.myrecipesapp.R;

public class Steak1 extends AppCompatActivity {

    private CheckBox favBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steak1);

        favBtn = (CheckBox) findViewById(R.id.favBtn);
        favBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(favBtn.isChecked()){
                    Toast.makeText(Steak1.this, "Item added to Favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Steak1.this, "Item removed from Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}