package com.jwu5.giphyapp;



import com.jwu5.giphyapp.model.Datum;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jiawei on 8/17/2017.
 */
public interface GiphyService {

    static final String API_KEY = "849d70e5f5f74406a34eee5530a250ec";

    @GET("trending")
    Observable<Datum> getTrendingGifs(@Query("api_key") String apiKey);
}
