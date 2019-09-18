package com.example.vlearn;

import com.example.vlearn.model.YahooFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FeedYahooAPI {    // API to fetch content from yahoo.com

    String BASE_URL ="https://yahoo.com/";

    @GET(BASE_URL + "{feed_name}/rss")
    Call<YahooFeed> getFeed(@Path("feed_name") String feed_name);

}
