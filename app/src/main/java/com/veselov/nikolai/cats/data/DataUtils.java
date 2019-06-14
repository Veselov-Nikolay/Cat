package com.veselov.nikolai.cats.data;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import ru.veselov.nikolai.cats.BuildConfig;

public final class DataUtils {
    private static final String TAG = DataUtils.class.getSimpleName();

    public static List<CatItem> generateNews() throws NetworkErrorException {
        final List<CatItem> news = new ArrayList<>();
        news.add(new CatItem(
            "Mr. Барсик",
            "http://pngimg.com/uploads/cat/cat_PNG50546.png"));

        news.add(new CatItem(
            "Mrs. Кэсис",
            "http://pngimg.com/uploads/cat/cat_PNG50537.png"));

        news.add(new CatItem(
            "Mr. Ферруцио",
            "http://pngimg.com/uploads/cat/cat_PNG50525.png"
        ));
        news.add(new CatItem(
            "Mr. Клементе",
            "http://pngimg.com/uploads/cat/cat_PNG50511.png"
        ));
        news.add(new CatItem(
            "Mr. Грампи",
            "http://pngimg.com/uploads/cat/cat_PNG50498.png"
        ));
        news.add(new CatItem(
            "Mr. Юппи",
            "http://pngimg.com/uploads/cat/cat_PNG50480.png"
        ));

        news.add(new CatItem(
                "Mrs. Шелли",
                "http://pngimg.com/uploads/cat/cat_PNG50433.png"));


        news.add(new CatItem(
                "Mr. Кактус",
                "http://pngimg.com/uploads/cat/cat_PNG50425.png"));


        news.add(new CatItem(
                "Mr. Леопардо",
                "http://pngimg.com/uploads/cat/cat_PNG120.png"));


        news.add(new CatItem(
                "Mrs. Жуля",
                "http://pngimg.com/uploads/cat/cat_PNG104.png"));


        return news;
    }

    public static Single<List<CatItem>> observeNews() {
        return Single.create(emitter -> {
            try {
                List<CatItem> news = generateNews();
                emitter.onSuccess(news);
            } catch (NetworkErrorException ex) {
                if (!emitter.tryOnError(ex) && BuildConfig.DEBUG) {
                    Log.e("DataUtils", "observeNews error handler caught an error", ex);
                }
            }
        });
    }


    private DataUtils() {
        throw new AssertionError("No instances");
    }

}
