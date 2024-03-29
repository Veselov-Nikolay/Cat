package com.veselov.nikolai.cats.cats;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.veselov.nikolai.cats.R;
import com.veselov.nikolai.cats.data.CatItem;
import com.veselov.nikolai.cats.data.DataUtils;
import com.veselov.nikolai.cats.details.CatDetailsActivity;
import com.veselov.nikolai.cats.utils.Utils;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class CatListActivity extends AppCompatActivity {

    public static final String TAG = CatListActivity.class.getSimpleName();

    @Nullable private ProgressBar progress;
    @Nullable private RecyclerView recycler;
    @Nullable private CatAdapter adapter;

    @Nullable private View error;
    @Nullable private Button errorAction;

    @Nullable private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        progress = findViewById(R.id.progress);
        recycler = findViewById(R.id.recycler);
        error = findViewById(R.id.error_layout);
        errorAction = findViewById(R.id.action_button);

        adapter = new CatAdapter(this, catItem -> CatDetailsActivity.start(this, catItem));
        recycler.setAdapter(adapter);
        recycler.addItemDecoration(new CatItemDecoration(getResources().getDimensionPixelSize(R.dimen.spacing_micro)));

        errorAction.setOnClickListener(view -> loadItems());

        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            final int columnsCount = getResources().getInteger(R.integer.landscape_news_columns_count);
            recycler.setLayoutManager(new GridLayoutManager(this, columnsCount));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadItems();
    }

    @Override
    protected void onStop() {
        super.onStop();
        showProgress(false);

        Utils.disposeSafe(disposable);
        disposable = null;
    }


    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        return super.onOptionsItemSelected ( item );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter = null;
        recycler = null;
        progress = null;
    }

    private void loadItems() {
        showProgress(true);
        disposable = DataUtils.observeNews()
                               .subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe(this::updateItems,
                                          this::handleError);
    }

    private void updateItems(@Nullable List<CatItem> news) {
        if (adapter != null) adapter.replaceItems(news);

        Utils.setVisible(recycler, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(error, false);
    }

    private void handleError(Throwable th) {
        if (Utils.isDebug()) {
            Log.e(TAG, th.getMessage(), th);
        }
        Utils.setVisible(error, true);
        Utils.setVisible(progress, false);
        Utils.setVisible(recycler, false);
    }

    private void showProgress(boolean shouldShow) {
        Utils.setVisible(progress, shouldShow);
        Utils.setVisible(recycler, !shouldShow);
        Utils.setVisible(error, !shouldShow);
    }
}
