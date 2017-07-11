package uk.ac.kent.am2230.kentnewsfeed;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsDetailsFragment extends Fragment {

    private NetworkImageView photo;
    private TextView title;
    private TextView date;
    private TextView detailTxt;
    private FloatingActionButton btnShare;
    private Intent shareIntent;
    private FloatingActionButton btnFavourite;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        photo = (NetworkImageView) view.findViewById(R.id.details_imageView);
        title = (TextView) view.findViewById(R.id.details_title);
        date = (TextView) view.findViewById(R.id.details_date_text);
        detailTxt = (TextView) view.findViewById(R.id.details_info);
        btnShare = (FloatingActionButton) view.findViewById(R.id.shareFloatingActionButton);
        btnFavourite = (FloatingActionButton) view.findViewById(R.id.favFloatingActionButton);


        return view;
    }



    public void updateDetails(int id) {
        final News news = NewsModel.getInstance().getNewsList().get(id);

        Log.d("NewsDetailsFragment", "Got " + news.getTitle());
        title.setText(news.getTitle());
        detailTxt.setText(news.getShort_info());
        date.setText(news.getDate());
        photo.setImageUrl(news.getImg_url(), NewsApp.getInstance().getImageLoader());

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent = new Intent(Intent.ACTION_SEND);
                Uri imageUri = Uri.parse(news.getImg_url());
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Kent News Feed!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, news.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, news.getShort_info());
                shareIntent.setType("image/jpg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });


        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final EditText inputTitle = new EditText(getActivity());

                builder.setTitle("New Bookmark");
                builder.setMessage("Name: ");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                inputTitle.setLayoutParams(lp);
                builder.setView(inputTitle);
                builder.setCancelable(true);

                inputTitle.setText(news.getTitle());
                builder.setPositiveButton(
                        "Save",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               // Log.e("Input title: ", news.getTitle());
                                startActivity(new Intent(getActivity(), BookmarkActivity.class));
                            }
                        }
                );
                AlertDialog dlg = builder.create();
                dlg.show();

            }
        });

        /*btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            startActivity(new Intent(getContext(), BookmarkActivity.class));

            }
        });*/

    }
}
