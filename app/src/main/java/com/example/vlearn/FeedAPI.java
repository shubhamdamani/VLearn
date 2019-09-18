package com.example.vlearn;

import com.example.vlearn.model.Feed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeedAPI {      // API to fetch content from reddit.com

    String BASE_URL = "https://www.reddit.com/r/";   //static part

    // Dynamic feed
    @GET(BASE_URL + "{feed_name}/.rss")
    Call<Feed> getFeed(@Path("feed_name") String feed_name);        // feed class ka object k type m return hoga xml se jab contact karenge

    /*@GET("https://reddit.com/r/earthporn/.rss")
    Call<Feed> getFeed();*/



}
