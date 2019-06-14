package com.veselov.nikolai.cats.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import ru.veselov.nikolai.cats.R;
import com.veselov.nikolai.cats.data.CatItem;

public class CatDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ITEM = "extra:newsItem";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final CatItem newsItem = (CatItem) getIntent().getSerializableExtra(EXTRA_NEWS_ITEM);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(newsItem.getTitle());
        }

        final ImageView imageView = findViewById(R.id.details_image);
        final TextView titleView = findViewById(R.id.details_title);

        Glide.with(this)
             .load(newsItem.getImageUrl())
             .transition(DrawableTransitionOptions.withCrossFade())
             .into(imageView);

        titleView.setText(newsItem.getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void start(@NonNull Context context, @NonNull CatItem newsItem) {
        context.startActivity(new Intent(context, CatDetailsActivity.class).putExtra(EXTRA_NEWS_ITEM, newsItem));
    }

}
