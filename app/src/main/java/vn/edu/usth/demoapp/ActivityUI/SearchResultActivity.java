package vn.edu.usth.demoapp.ActivityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import vn.edu.usth.demoapp.R;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        if(b != null)
            value = b.getString("search_param");

        // update query_text
        TextView query_text = findViewById(R.id.query_text);
        query_text.setText("Search result for: " + value);

    }
}