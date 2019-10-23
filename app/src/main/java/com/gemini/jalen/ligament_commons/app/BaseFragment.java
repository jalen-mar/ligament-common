package com.gemini.jalen.ligament_commons.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.gemini.jalen.ligament.app.GeneralFragment;
import com.gemini.jalen.ligament.databinding.WindowModel;
import com.gemini.jalen.ligament.widget.Loader;
import com.gemini.jalen.ligament_commons.dagger.AndroidSupportInjection;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.internal.Beta;

@Beta
public class BaseFragment<T extends ViewDataBinding> extends GeneralFragment<T> implements HasAndroidInjector {
    private Loader loader;

    @Override
    public void onAttach(@NonNull Context context) {
        if (isSupportIOC()) {
            AndroidSupportInjection.inject(this);
        }
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loader = new Loader();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected boolean isSupportIOC() {
        return true;
    }

    @Override
    public <M extends WindowModel> M wrap(M vm) {
        M result = super.wrap(vm);
        result.getLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean it) {
                if (it) {
                    loader.show(getChildFragmentManager(), loader.toString());
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
