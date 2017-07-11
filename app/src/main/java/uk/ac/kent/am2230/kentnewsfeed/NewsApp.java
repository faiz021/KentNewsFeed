package uk.ac.kent.am2230.kentnewsfeed;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by am2230 on 30/03/2017.
 */

public class NewsApp extends Application {

    private static NewsApp instance;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;

        requestQueue = Volley.newRequestQueue(this);

        int cacheSize = 4*1024*1024;
        imageLoader = new ImageLoader(requestQueue, new LruBitmapCache(cacheSize));

    }

    public static NewsApp getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
