package uk.ac.kent.am2230.kentnewsfeed;

import android.util.Log;

/**
 * Created by am2230 on 30/03/2017.
 */

public class News {
    private int record_id;
    private String title, date, short_info, img_url;

    public News(String img_url, int record_id, String title, String date, String short_info) {
        this.record_id = record_id;
        this.title = title;
        this.date = date;
        this.short_info = short_info;
        this.img_url = img_url;
    }

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShort_info() {
        return short_info;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public boolean matchesKeywords(String[] keywords) {
        for (int i = 0; i < keywords.length; i++)
        {   String title = getTitle();
            Log.e("title",title);
            String keyword = keywords[i];
            Log.e("keyword",keyword);
            if (title.toLowerCase().contains(keyword.toLowerCase()))
                return true;
        }
        return false;
    }
}