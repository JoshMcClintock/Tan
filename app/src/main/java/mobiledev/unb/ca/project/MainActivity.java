package mobiledev.unb.ca.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btngocalendar;
    private EditText items;
    protected static String old_userInput;
    protected static String file_path;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = (EditText) findViewById(R.id.items);
        btngocalendar = (Button) findViewById(R.id.btngocalendar);

        Intent incoming = getIntent();
        file_path = incoming.getStringExtra("file_path");

        RetriveData(file_path);

        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        old_userInput = items.getText().toString();
    }

    @Override
    protected  void onPause() {
        super.onPause();

        old_userInput = items.getText().toString();
    }

    public void RetriveData(String file_path){
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard, "userInput.txt");

        Log.i(TAG, file.getAbsolutePath());

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                Log.i(TAG, file.getAbsolutePath());
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }

        Log.i(TAG, text.toString());
        if(text.toString() != null) {
            items.setText(text.toString());
            old_userInput = items.getText().toString();
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}