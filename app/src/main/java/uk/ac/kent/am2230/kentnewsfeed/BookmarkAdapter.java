package uk.ac.kent.am2230.kentnewsfeed;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by am2230 on 22/04/2017.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private ArrayList<News> news;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookmarkName;

        public ViewHolder(View itemView) {
            super(itemView);
            bookmarkName = (TextView) itemView.findViewById(R.id.bookmark_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ViewHolder.this.getAdapterPosition();

                }
            });

        }
    }
    private Context context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_cell, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bookmarkName.setText(news.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
