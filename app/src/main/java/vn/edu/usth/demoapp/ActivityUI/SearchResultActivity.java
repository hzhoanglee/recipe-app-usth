package vn.edu.usth.demoapp.ActivityUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import vn.edu.usth.demoapp.R;

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle b = getIntent().getExtras();
        String value = ""; // or other values
        String title = ""; // or other values
        if(b != null) {
            String search_param = b.getString("search_param");
            if (Objects.equals(b.getString("type"), "category")) {
                value = "Data for Category: " + search_param;
                title = getString(R.string.title_nav_category);
            }
            else {
                value = "Data for Search: " + search_param;
                title = getString(R.string.Search);
            }
            if (search_param.length() > 12) {
                search_param = search_param.substring(0, 12) + "...";
            }
            setTitle(title + ": " + search_param);
        }

        // update query_text
        TextView query_text = findViewById(R.id.query_text);
        query_text.setText(value);

        handleBack();

    }

    private void setTitle(String title) {
        TextView textView = findViewById(R.id.textViewTitle);
        textView.setText(title);
    }

    private void handleBack() {
        findViewById(R.id.SettingBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}