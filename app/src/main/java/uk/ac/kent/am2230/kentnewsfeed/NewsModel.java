package uk.ac.kent.am2230.kentnewsfeed;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by am2230 on 11/04/2017.
 */

public class NewsModel {
    private static NewsModel ourInstance = new NewsModel();

    public static NewsModel getInstance() {
        return ourInstance;
    }

    public static ArrayList<News> newsList = new ArrayList<News>();

    private OnListUpdateListener listUpdateListener;

    private NewsModel() {
    }

    public ArrayList<News> getNewsList() {
        return newsList;
    }

    private Response.Listener<JSONArray> netListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            //Clear previous data
            newsList.clear();
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject object = response.getJSONObject(i);


                    News newsData = new News(
                            object.getString("image_url"),
                            object.getInt("record_id"),
                            object.getString("title"),
                            object.getString("date"),
                            object.getString("short_info")
                    );
                    newsList.add(newsData);
                }
            } catch (JSONException e) {
               //Error in json data
            }
            MainActivity.newsList = newsList;
            notifyListener();
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    public void loadData() {
        //Request object
        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                "http://www.efstratiou.info/projects/newsfeed/getList.php",
                netListener, errorListener);

        //Make request
        NewsApp.getInstance().getRequestQueue().add(arrayRequest);
    }

    public void setOnListUpdateListener(OnListUpdateListener listUpdateListener) {
        this.listUpdateListener = listUpdateListener;
    }

    public interface OnListUpdateListener {
        void onListUpdate(ArrayList<News> newsList);
    }

    public void notifyListener() {
        if (listUpdateListener != null) {
            listUpdateListener.onListUpdate(newsList);
        }
    }


}
