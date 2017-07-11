package uk.ac.kent.am2230.kentnewsfeed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<News> news;
    private List<News> filteredNews;
    private ArrayList<News> arrayList = new ArrayList<News>();

    NewsListAdapter(ArrayList<News> arrayList){this.arrayList=arrayList;}

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView newsTitle;
        private TextView shortNews;
        private TextView date;
        private NetworkImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            newsTitle = (TextView) itemView.findViewById(R.id.title_text);
           // shortNews = (TextView) itemView.findViewById(R.id.short_text);
            date = (TextView) itemView.findViewById(R.id.date_text);
            image = (NetworkImageView) itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    //Toast.makeText(v.getContext(), "Clicked item "+position, Toast.LENGTH_SHORT).show();

                    // Using the listener we can notify the activity
                    if (listener != null) {
                        listener.onNewsItemClicked(position);
                    }
                }
            });
        }

        public void setData(News news) {
            newsTitle.setText(news.getTitle());
           // shortNews.setText(news.getShort_info());
            date.setText(news.getDate());
            image.setImageUrl(news.getImg_url(), NewsApp.getInstance().getImageLoader());
           // image.setImageResource(news.getImg_url());
        }
    }

    public void setFilter(ArrayList<News> newList){

        arrayList = new ArrayList<>();
        arrayList.addAll(newList);
        notifyDataSetChanged();
    }

    private final static String _TAG = "NewsListAdapter";

    private NewsModel model = NewsModel.getInstance();
    // The listener pointer
    private onNewsListListener activitylistener;

    private Context context;
    private NewsListFragment.OnNewsItemClickedListener listener;
    // We expect the context object to also support the onNewsListListener interface
    public NewsListAdapter(Context ctx) {
        super();
        context = ctx;
        if (context instanceof NewsListFragment.OnNewsItemClickedListener) {
            listener = (NewsListFragment.OnNewsItemClickedListener) context;
        } else {
            Log.e(_TAG, "Activity does not support OnNewsListListener interface");
        }
    }

    @Override
    public int getItemCount() {
        return model.getNewsList().size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = model.getNewsList().get(position);
        System.out.println(model.newsList);
        holder.setData(news);
    }

    /**
     * Communication with Activity
     * We define a listener interface for contacting the activity
     * The activity needs to implement that interface
     *
     * We also hold a private variable with a pointer to the listener (see above)
     */

    public interface onNewsListListener {
        public void onNewsListItemClicked(int position);
    }



    public void filterItems(String[] keywords) {
        arrayList = new ArrayList<>();
        for (int i = 0; i < model.getNewsList().size(); i++) {
            News n = model.getNewsList().get(i);
            if (n.matchesKeywords(keywords))
                arrayList.add(n);

        }
        NewsModel.newsList = arrayList;
        notifyDataSetChanged();
    }

    public void clearFilters() {
        copyItems();
        notifyDataSetChanged();
    }

    public void copyItems() {
        filteredNews = new ArrayList<>();
        for (int i = 0; i < news.size(); i++) {
            filteredNews.add(news.get(i));
        }
    }

}