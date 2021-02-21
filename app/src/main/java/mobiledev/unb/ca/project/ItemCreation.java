package mobiledev.unb.ca.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.ArrayList;

import mobiledev.unb.ca.project.util.JsonUtil;

public class ItemCreation extends AppCompatActivity {

    private Button add_button;
    private ArrayList editText = new ArrayList();
    private TextView result;
    private static final String TAG = "item_creation";
    private JSONArray jarray;
    private AlertDialog alertDialog;
    private String date;

    private JsonUtil jsonUtil;

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String blockCharacterSet = "~#^|$%*!@/()-'\":;,?{}=!$^';,?×÷<>{}€£¥₩%~`¤♡♥_|《》¡¿°•○●□■◇◆♧♣▲▼▶◀↑↓←→☆★▪:-);-):-D:-(:'(:O 1234567890";
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_creation);

        MainActivity.verifyStoragePermissions(this);

        add_button = (Button) findViewById(R.id.Addbutton);
        result = (TextView) findViewById(R.id.result);

        Intent incoming = getIntent();
        date = incoming.getStringExtra("date");

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });
    }

    private void alertDialog(){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Add Item");

        alert.setCancelable(false);

        final EditText input = new EditText(this);
        final EditText input2 = new EditText(this);
        input.setFilters(new InputFilter[]{ filter });
        input.setHint("Item(Character only)");
        input2.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        input2.setHint("Price(Number only)");

        layout.addView(input);
        layout.addView(input2);

        alert.setView(layout);

        alert.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent getDate = getIntent();
                        date = getDate.getStringExtra("date");

                        if(input.getText().toString().length() > 0 && input2.getText().toString().length() > 0) {
                            JSONObject userInput = new JSONObject();
                            try {
                                if(MainActivity.old_userInput != null && MainActivity.emptyItemList) {
                                    jarray = new JSONObject(MainActivity.old_userInput).getJSONArray("Items");
                                }
                                else{
                                    jarray = new JSONArray();
                                }
                                userInput.put("date",date);
                                userInput.put("Item", input.getText().toString());
                                userInput.put("Price", input2.getText().toString());
                                jarray.put(userInput);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JSONObject groceryItemsList = new JSONObject();
                            try {
                                jarray = JsonUtil.ModifyElements(jarray);
                                groceryItemsList.put("Items", jarray);
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                            jsonUtil = new JsonUtil(MainActivity.old_userInput, groceryItemsList);

                            Intent refresh = new Intent(ItemCreation.this, MainActivity.class);
                            refresh.putExtra("file_path", jsonUtil.createJsonFile());
                            MainActivity.emptyItemList = true;
                            startActivity(refresh);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "No empty value accepted", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ItemCreation.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        alertDialog = alert.create();
        alertDialog.show();
    }

}
