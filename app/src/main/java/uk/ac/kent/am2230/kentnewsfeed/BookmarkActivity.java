package uk.ac.kent.am2230.kentnewsfeed;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView bookmarkListView;
    private BookmarkAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bookmarkListView = (RecyclerView) findViewById(R.id.bookmarks_list);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        bookmarkListView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(this, layoutManager.getOrientation());
        bookmarkListView.addItemDecoration(divider);

        adapter = new BookmarkAdapter();
            bookmarkListView.setAdapter(adapter);



    }



}
