package mobiledev.unb.ca.project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import mobiledev.unb.ca.project.model.GroceryItems;
import mobiledev.unb.ca.project.util.JsonUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btngocalendar;
    private RecyclerView items;
    protected static String old_userInput;
    protected static boolean emptyItemList = false;

    private JSONArray jsonArray;

    private ArrayList<GroceryItems> list;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private JsonUtil jsonUtil;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btngocalendar = (Button) findViewById(R.id.btngocalendar);

        jsonUtil = new JsonUtil(old_userInput);

        try {
            jsonArray = GetArray();
        } catch (JSONException | IOException | ParseException e) {
            Log.i(TAG, e.getMessage());
        }

        list = new ArrayList<GroceryItems>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject temp = (JSONObject) jsonArray.get(i);
                    if((temp.has("Another"))) {
                        list.add(new GroceryItems(temp.getString("Item"), temp.getString("date"), temp.getString("Price"), (JSONArray) temp.get("Another")));
                    }
                    else{
                        list.add(new GroceryItems(temp.getString("Item"), temp.getString("date"), temp.getString("Price")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }catch (NullPointerException e){
            ;
        }

        items = findViewById(R.id.items);
        MyAdapter myAdapter = new MyAdapter(list);
        myAdapter.notifyItemRangeChanged(0, list.size());
        myAdapter.notifyDataSetChanged();
        items.setAdapter(myAdapter);

        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private JSONArray GetArray() throws JSONException, IOException, ParseException {
        JSONArray jarray = null;
        if(jsonUtil.CheckFIleLines() > 0){
            emptyItemList = true;
            //jarray = jsonUtil.ModifyElements();
            old_userInput = jsonUtil.RetrieveData();
            jarray = new JSONObject(old_userInput).getJSONArray("Items");
            jarray = jsonUtil.SortArray(jarray);
        }
        return jarray;
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<GroceryItems> mDataset;

        public MyAdapter(ArrayList<GroceryItems> myDataset) {
            mDataset = myDataset;
        }

        // ViewHolder represents an individual item to display. In this case
        // it will just be a single TextView (displaying the title of a course)
        // but RecyclerView gives us the flexibility to do more complex things
        // (e.g., display an image and some text).
        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(TextView v) {
                super(v);
                mTextView = v;
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);

            return new ViewHolder(v);
        }


        // onBindViewHolder binds a ViewHolder to the data at the specified
        // position in mDataset
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final GroceryItems item = list.get(position);

            holder.mTextView.setText(item.getDate());

            holder.mTextView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("item", item.getName());
                    intent.putExtra("date", item.getDate());
                    intent.putExtra("price", item.getPrice());

                    if(item.getAnotherItem().length() > 0){
                        try {
                            intent.putExtra("Length", item.getAnotherItem().length());
                            for(int i = 0; i < item.getAnotherItem().length(); i++) {
                                JSONObject temp = (JSONObject) item.getAnotherItem().get(i);
                                intent.putExtra("another_item_price" + i, temp.getString("Price"));
                                intent.putExtra("another_item_name" + i, temp.getString("Item"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}