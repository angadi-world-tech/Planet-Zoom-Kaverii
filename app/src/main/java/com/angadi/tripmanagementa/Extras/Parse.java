package com.angadi.tripmanagementa.Extras;

import android.util.Log;

import com.angadi.tripmanagementa.Model.SignUpArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Parse
{
    public static JsonObject Signup(String name, String email, String mobile , String password, String state, String city, String address)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.username,name);
        jsonObject.addProperty(Keys.email,email);
        jsonObject.addProperty(Keys.mobile,mobile);
        jsonObject.addProperty(Keys.password,password);
        jsonObject.addProperty(Keys.state, state);
        jsonObject.addProperty(Keys.city,city);
        jsonObject.addProperty(Keys.address,address);


        return jsonObject;
    }



    public static ArrayList<SignUpArray> getSigupdata(String s)
    {

        ArrayList<SignUpArray> array_login = new ArrayList<>();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            jsonObject.get("name");
            jsonObject.get("email");
            jsonObject.get("mobile");
            jsonObject.get("adhar");

            Log.d("Array_darta", String.valueOf(array_login));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  array_login;
    }

}
