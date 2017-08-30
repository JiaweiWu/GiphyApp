package com.jwu5.giphyapp

import android.content.Context

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jwu5.giphyapp.network.model.GiphyModel

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.util.LinkedHashMap

/**
 * Created by Jiawei on 8/29/2017.
 */
object utils {
    fun saveInFile(favorites: LinkedHashMap<String, GiphyModel>?, filename: String, context: Context) {
        try {
            val fos = context.openFileOutput(filename, 0)
            val out = BufferedWriter(OutputStreamWriter(fos))

            val gson = Gson()
            gson.toJson(favorites, out)
            out.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun loadFromFile(filename: String, context: Context): LinkedHashMap<String, GiphyModel>? {
        try {
            val fis = context.openFileInput(filename)
            val `in` = BufferedReader(InputStreamReader(fis))

            val gson = Gson()
            val listType = object : TypeToken<LinkedHashMap<String, GiphyModel>>() {

            }.type

            return gson.fromJson<LinkedHashMap<String, GiphyModel>>(`in`, listType)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return null
    }
}
