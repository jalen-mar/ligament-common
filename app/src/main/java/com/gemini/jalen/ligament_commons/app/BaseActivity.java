package com.gemini.jalen.ligament_commons.app;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gemini.jalen.ligament.app.GeneralActivity;
import com.gemini.jalen.ligament.databinding.WindowModel;
import com.gemini.jalen.ligament.widget.Loader;
import com.gemini.jalen.ligament_commons.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.internal.Beta;

@Beta
public class BaseActivity<T extends ViewDataBinding> extends GeneralActivity<T> implements View.OnClickListener, HasAndroidInjector {
    public static int ACTION_UNABLE = 0;
    public static int ACTION_TITLE = 1;
    public static int ACTION_NAVIGATION = 2;

    protected Loader loader;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        loader = new Loader();
        super.onCreate(savedInstanceState);
        initToolbar(toolbar = findViewById(R.id.toolLayout));
    }

    private void initToolbar(Toolbar toolbar) {
        int type = enableToolbar();
        if (type > ACTION_UNABLE) {
            setSupportActionBar(toolbar);
            if (type == ACTION_TITLE) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                ((TextView) toolbar.findViewById(R.id.tvTitle)).setText(getTitleValue());
            } else {
                getSupportActionBar().setTitle(getTitleValue());
                toolbar.findViewById(R.id.tvTitle).setVisibility(View.GONE);
            }
            int background = getTitleBackground();
            if (background != -1) {
                toolbar.setBackgroundColor(getResources().getColor(background));
            }
            if (type == ACTION_NAVIGATION) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(this);
            }
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    protected int enableToolbar() {
        return ACTION_NAVIGATION;
    }

    protected String getTitleValue() {
        return null;
    }

    protected int getTitleBackground() {
        return -1;
    }

    @Override
    protected void onDestroy() {
        loader.dismiss();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected View getContentView(ViewGroup parentView) {
        View contentView = getLayoutInflater().inflate(R.layout.view_base, parentView, false);
        int resId = getLayout();
        if (resId != View.NO_ID) {
            View view = getLayoutInflater().inflate(resId, (ViewGroup) contentView, false);
            if (getContentBelowParent()) {
                ((RelativeLayout.LayoutParams) view.getLayoutParams())
                        .addRule(RelativeLayout.BELOW, R.id.toolLayout);
            }
            if (isSupportDataBinding()) {
                binder = DataBindingUtil.bind(view);
            }
            if (ViewCompat.getFitsSystemWindows(view)) {
                view.setFitsSystemWindows(false);
                contentView.setFitsSystemWindows(true);
            }
            ((ViewGroup) contentView).addView(view);
        }
        return contentView;
    }

    protected boolean getContentBelowParent() {
        return true;
    }

    @Override
    public <M extends WindowModel> M wrap(M vm) {
        M result = super.wrap(vm);
        result.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean it) {
                if (it) {
                    loader.show(getSupportFragmentManager(), loader.toString());
                } else {
                    loader.dismiss();
                }
            }
        });
        return result;
    }

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
