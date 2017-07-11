package uk.ac.kent.am2230.kentnewsfeed;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class NewsListFragment extends Fragment implements NewsModel.OnListUpdateListener{


    private OnNewsItemClickedListener mListener;
    private RecyclerView newsListView;
    private LinearLayoutManager layoutManager;
    public static NewsListAdapter adapter;
    private static final String TAG = "NewsListFragment";
    private List<News> news;

    public NewsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_news_list, container, false);

        newsListView = (RecyclerView) view.findViewById(R.id.news_list_view);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Passing the activity as attribute. This will be assigned to the listener in the adapter.
        adapter = new NewsListAdapter(getActivity());
        newsListView.setAdapter(adapter);

        newsListView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        newsListView.addItemDecoration(divider);

        //Connect to the model
        NewsModel newsModel = NewsModel.getInstance();
        newsModel.setOnListUpdateListener(this);
        newsModel.loadData();


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsItemClickedListener) {
            mListener = (OnNewsItemClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewsItemClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnNewsItemClickedListener {
        // Called when a news is clicked
        void onNewsItemClicked(int position);
    }

    @Override
    public void onListUpdate(ArrayList<News> newsList) {

        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }

}
