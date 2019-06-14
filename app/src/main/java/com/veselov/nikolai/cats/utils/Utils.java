package com.veselov.nikolai.cats.utils;


import android.view.View;

import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;
import ru.veselov.nikolai.cats.BuildConfig;

public final class Utils {
    public static void disposeSafe(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public static void setVisible(@Nullable View view, boolean show) {
        if (view == null) return;

        int visibility = show ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private Utils() {
        throw new AssertionError("No instances");
    }
}
