package com.gemini.jalen.ligament_commons.dagger;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.Fragment;

import dagger.android.AndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.internal.Preconditions;

public final class AndroidSupportInjection {
    private static final String TAG = "dagger.androidx.fragment";

    public static void inject(Fragment fragment) {
        Preconditions.checkNotNull(fragment, "fragment");
        HasAndroidInjector hasAndroidInjector = findHasAndroidInjectorForFragment(fragment);
        if (Log.isLoggable("dagger.android.support", Log.DEBUG)) {
            Log.d("dagger.android.support", String.format("An injector for %s was found in %s", fragment.getClass().getCanonicalName(), hasAndroidInjector.getClass().getCanonicalName()));
        }

        inject(fragment, hasAndroidInjector);
    }

    private static HasAndroidInjector findHasAndroidInjectorForFragment(Fragment fragment) {
        Fragment parentFragment = fragment;

        do {
            if ((parentFragment = parentFragment.getParentFragment()) == null) {
                Activity activity = fragment.getActivity();
                if (activity instanceof HasAndroidInjector) {
                    return (HasAndroidInjector)activity;
                }

                if (activity.getApplication() instanceof HasAndroidInjector) {
                    return (HasAndroidInjector)activity.getApplication();
                }

                throw new IllegalArgumentException(String.format("No injector was found for %s", fragment.getClass().getCanonicalName()));
            }
        } while(!(parentFragment instanceof HasAndroidInjector));

        return (HasAndroidInjector)parentFragment;
    }

    private static void inject(Object target, HasAndroidInjector hasAndroidInjector) {
        AndroidInjector<Object> androidInjector = hasAndroidInjector.androidInjector();
        Preconditions.checkNotNull(androidInjector, "%s.androidInjector() returned null", hasAndroidInjector.getClass());
        androidInjector.inject(target);
    }

    private AndroidSupportInjection() {}
}

