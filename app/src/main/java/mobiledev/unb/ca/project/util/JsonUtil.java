package mobiledev.unb.ca.project.util;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.jar.JarInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {

    private String old_userInput;
    private JSONObject new_userInput;
    private static int counter = 0;

    public static int extra_item_counter = 1;

    private final static String Tag = "debug";

    public JsonUtil(String old_userInputIn, JSONObject new_userInputIn){
        old_userInput = old_userInputIn;
        new_userInput = new_userInputIn;
    }

    public JsonUtil(String old_userInputIn){
        old_userInput = old_userInputIn;
    }

    public String createJsonFile(){
        File root = Environment.getExternalStorageDirectory();

        if(!root.exists())
            root.mkdirs();

        File gpxfile = new File(root, "userInput.json");

        Log.i("debug new userInput", new_userInput.toString());

        FileWriter writer = null;
        try {
            writer = new FileWriter(gpxfile);
            writer.append(new_userInput.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gpxfile.getAbsolutePath();
    }

    public String RetrieveData() {
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard, "userInput.json");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder text = new StringBuilder();

            while ((line = br.readLine()) != null) {
                text.append(line + "\n");
            }

            br.close();

            return text.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int CheckFIleLines(){
        File sdcard = Environment.getExternalStorageDirectory();

        File file = new File(sdcard, "userInput.json");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder text = new StringBuilder();

            while ((line = br.readLine()) != null) {
                text.append(line);
                counter++;
            }

            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public JSONArray SortArray(JSONArray jsonArray) throws JSONException, ParseException {
        JSONObject temp;
        for(int i = 1; i < jsonArray.length(); i++){
            for(int j = i; j > 0; j--){
                Date dateI = new SimpleDateFormat("yyyy/MM/dd").parse(((JSONObject)jsonArray.get(j)).getString("date"));
                Date dateJ = new SimpleDateFormat("yyyy/MM/dd").parse(((JSONObject)jsonArray.get(j-1)).getString("date"));
                if(dateI.before(dateJ)){
                    temp = (JSONObject) jsonArray.get(j);
                    jsonArray.put(j, jsonArray.get(j-1));
                    jsonArray.put(j-1, temp);
                }
            }
        }
        return jsonArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static JSONArray ModifyElements(JSONArray jarray) throws JSONException, IOException {

        Log.i("debug in file", jarray.toString() + " " + jarray.length());

        JSONArray extraList;
        for(int i = 0; i < jarray.length(); i++){
            extraList = new JSONArray();

            for(int j = i+1; j < jarray.length(); j++) {
                JSONObject temp = (JSONObject) jarray.get(i);
                JSONObject target = (JSONObject) jarray.get(j);

                if (temp.getString("date").equals(target.getString("date"))) {
                    target.remove("date");

                    if(temp.has("Another")) {
                        Log.i("debug modify", "has another");
                        extraList = (JSONArray) temp.get("Another");
                        extraList.put(target);
                        temp.remove("Another");
                        temp.put("Another", extraList);
                    }
                    else {
                        Log.i("debug modify", "no another i:" + temp);
                        extraList.put(target);
                        temp.put("Another", extraList);
                    }
                    jarray.put(i,temp);
                    if(jarray.length() > 1) {
                        Log.i("Main", "removed");
                        jarray.remove(j);
                        for(int k = 0; k < jarray.length(); k++){
                            if(jarray.get(k) == null) {
                                if(jarray.get(k+1) != null) {
                                    jarray.put(k, jarray.get(k + 1));
                                    break;
                                }
                                else{
                                    JSONArray temp_array = new JSONArray();
                                    for(int l = 0; l < k; l++){
                                        temp_array.put(l, jarray.get(l));
                                    }
                                    jarray = temp_array;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        Log.i("debug after removed", " " + jarray.length() + " " + jarray.toString());

        return jarray;
    }
}
