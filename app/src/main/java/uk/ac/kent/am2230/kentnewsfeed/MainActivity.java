package uk.ac.kent.am2230.kentnewsfeed;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.LayoutInflater;

import com.android.volley.toolbox.NetworkImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsListFragment.OnNewsItemClickedListener, SearchView.OnQueryTextListener, View.OnClickListener {

    private boolean hasTwoPanes;
    private ListView listView;
    //private List<News> newsList;
    private TextWatcher textWatcher;
    private NewsListAdapter adapter;
    private Fragment listFragment;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private TextView textViewEmail;
    private FirebaseAuth firebaseAuth;
    private NavigationView navigationView;
    private Menu menu;
    private Button btnLogout;
    private ArrayList<News> arrayList = new ArrayList<News>();

    public static ArrayList<News> newsList = new ArrayList<News>();
    private NewsModel newsModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.details_fragment) != null) {
            hasTwoPanes = true;
        } else {
            hasTwoPanes = false;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);



        //textViewEmail = (TextView) findViewById(R.id.nav_account);
        //btnLogout = (Button) findViewById(R.id.nav_logout);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem = menu.findItem(R.id.nav_account);
        String email = firebaseAuth.getCurrentUser().getEmail();
        menuItem.setTitle(email);
        Log.e("Email: ", email);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        break;
                    case R.id.nav_logout:
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                return false;
            }
        });
        //textViewEmail.setText("Hi "+ user.getEmail());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // btnLogout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                NewsModel.newsList = newsList;
                NewsListFragment.adapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsItemClicked(int position) {

        if (hasTwoPanes) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NewsDetailsFragment fragment = (NewsDetailsFragment) fragmentManager
                    .findFragmentById(R.id.details_fragment);
            fragment.updateDetails(position);

        } else {
            Intent intent = new Intent(this, NewsDetailsActivity.class);
            intent.putExtra("ITEM_ID", position);
            startActivity(intent);
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText.toString();
        String[] keywords = text.split(" ");
        NewsListFragment.adapter.filterItems(keywords);
        return true;
    }

    @Override
    public void onClick(View v) {


    }
}
