package mobiledev.unb.ca.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView txtView;
    private final static String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String item = intent.getStringExtra("item");
        String price = intent.getStringExtra("price");

        StringBuilder string = new StringBuilder();
        string.append("Name: " + item + "\n Price: " + price + "\n" + "Extra Items: \n ");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(date);

        int length = intent.getIntExtra("Length", 0);

        for(int i = 0; i < length; i++) {
            string.append(intent.getStringExtra("another_item_name" + i) + " " + intent.getStringExtra("another_item_price" + i) + "\n");
        }

        txtView = findViewById(R.id.description_textview);
        txtView.setText(string.toString());

        txtView.setMovementMethod(new ScrollingMovementMethod());
    }
}

