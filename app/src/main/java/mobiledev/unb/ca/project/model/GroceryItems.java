package mobiledev.unb.ca.project.model;

import org.json.JSONArray;
import org.json.JSONObject;

public class GroceryItems {
    private String name;
    private String price;
    private String date;
    private JSONArray extraItems = new JSONArray();

    public GroceryItems(String nameIn, String dateIn, String priceIn){
        name = nameIn;
        date = dateIn;
        price = priceIn;
    }

    public GroceryItems(String nameIn, String dateIn, String priceIn, JSONArray anotherItemIn){
        name = nameIn;
        date = dateIn;
        price = priceIn;
        extraItems = anotherItemIn;
    }

    public String getName(){
        return name;
    }

    public String getPrice(){
        return price;
    }

    public String getDate(){
        return date;
    }

    public JSONArray getAnotherItem(){
        return extraItems;
    }

}
