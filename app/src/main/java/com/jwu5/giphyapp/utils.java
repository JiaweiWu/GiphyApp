package com.jwu5.giphyapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jwu5.giphyapp.network.model.GiphyModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * Created by Jiawei on 8/29/2017.
 */
public class utils {
    public static void saveInFile(LinkedHashMap<String, GiphyModel> favorites, String filename, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(favorites, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedHashMap<String, GiphyModel> loadFromFile(String filename, Context context) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<LinkedHashMap<String, GiphyModel>>() {}.getType();

            return gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    };
}
