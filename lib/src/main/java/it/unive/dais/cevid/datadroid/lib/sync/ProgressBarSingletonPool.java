package it.unive.dais.cevid.datadroid.lib.sync;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by spano on 20/12/2017.
 */
public class ProgressBarSingletonPool extends SingletonPool<ProgressBar> {
    public ProgressBarSingletonPool(@NonNull ProgressBar x) {
        super(x);
    }

    @Override
    protected void onLastRelease() {
        content.setVisibility(View.GONE);
    }

    @Override
    protected void onFirstAcquire() {
        content.setVisibility(View.VISIBLE);
    }
}
