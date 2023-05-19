package com.xuanwenchao.metaphor.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.xuanwenchao.metaphor.exception.MetaphorException;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.manager
 * @Description: MetaphorActivityManager extens MetaphorManager for manage fragment base on Activity, optional strategy class
 * @date Dec 19,2022
 */
final class MetaphorActivityManager extends MetaphorManager {

    static MetaphorActivityManager createActivityManager(@NonNull AppCompatActivity activity) {
        return new MetaphorActivityManager(activity);
    }

    MetaphorActivityManager(AppCompatActivity activity) {
        super(activity);
        m_baseObject = activity;
        activity.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                OnMetaphorManagerDestroy();
            }
        });

        if (m_containerViewIds != null && m_containerViewIds.length > 0) {
            for (int cvid : m_containerViewIds) {
                this.addContainerViewID(cvid);
            }
        }
    }

    @Override
    public void addContainerViewID(int containerViewId) {
        if (m_baseObject == null) {
            throw new MetaphorException("No base fragment or activity be specified!");
        }

        if (m_containerViewMap.get(containerViewId) == null) {
            m_containerViewMap.put(containerViewId, ((AppCompatActivity) m_baseObject).getSupportFragmentManager());
        }
    }
}
