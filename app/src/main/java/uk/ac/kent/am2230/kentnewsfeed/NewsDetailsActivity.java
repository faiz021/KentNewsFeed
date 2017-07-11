package uk.ac.kent.am2230.kentnewsfeed;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Intent intent = getIntent();
        int itemId = intent.getIntExtra("ITEM_ID", 0);

        // Get fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        NewsDetailsFragment fragment = (NewsDetailsFragment) fragmentManager
                .findFragmentById(R.id.details_fragment);
        fragment.updateDetails(itemId);
    }


}
