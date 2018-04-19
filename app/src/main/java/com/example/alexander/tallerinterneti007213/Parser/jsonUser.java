package com.example.alexander.tallerinterneti007213.Parser;

import com.example.alexander.tallerinterneti007213.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 17/04/2018.
 */

public class jsonUser {

    public static List<User> getData(String content) throws JSONException {

        JSONArray jsonArray = new JSONArray(content);

        List<User> userList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){

            JSONObject item = jsonArray.getJSONObject(i);

            User user = new User();
            user.setCodigo(item.getString("codigo"));
            user.setNombre(item.getString("nombre"));
            user.setEdad(item.getString("edad"));
            user.setCorreo(item.getString("correo"));
            user.setPass(item.getString("pass"));

            userList.add(user);

        }

        return userList;
    }

}
