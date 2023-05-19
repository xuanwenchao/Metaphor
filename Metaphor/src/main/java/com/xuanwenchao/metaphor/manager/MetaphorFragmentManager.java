package com.xuanwenchao.metaphor.manager;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.xuanwenchao.metaphor.exception.MetaphorException;
import com.xuanwenchao.metaphor.interfaces.IMetaphorManager;
import com.xuanwenchao.metaphor.interfaces.IMetaphorMessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.manager
 * @Description: FragmentManager extens MetaphorManager for manage fragment base on fragment, optional strategy class
 * @date Dec 05,2022
 */
final class MetaphorFragmentManager extends MetaphorManager {

    static MetaphorFragmentManager createFragmentManager(@NonNull Fragment fragment) {
        return new MetaphorFragmentManager(fragment);
    }

    MetaphorFragmentManager(@NonNull Fragment fragment) {
        super(fragment);
        m_baseObject = fragment;
        fragment.getFragmentManager().registerFragmentLifecycleCallbacks(new androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentPreAttached(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentPreAttached(fm, f, context);
            }

            @Override
            public void onFragmentAttached(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentAttached(fm, f, context);
            }

            @Override
            public void onFragmentPreCreated(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentPreCreated(fm, f, savedInstanceState);
            }

            @Override
            public void onFragmentCreated(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
            }

            @Override
            public void onFragmentActivityCreated(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState);
            }

            @Override
            public void onFragmentViewCreated(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            }

            @Override
            public void onFragmentStarted(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStarted(fm, f);
            }

            @Override
            public void onFragmentResumed(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);
            }

            @Override
            public void onFragmentPaused(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentPaused(fm, f);
            }

            @Override
            public void onFragmentStopped(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStopped(fm, f);
            }

            @Override
            public void onFragmentSaveInstanceState(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
                super.onFragmentSaveInstanceState(fm, f, outState);
            }

            @Override
            public void onFragmentViewDestroyed(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
            }

            @Override
            public void onFragmentDestroyed(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDestroyed(fm, f);
                OnMetaphorManagerDestroy();
            }

            @Override
            public void onFragmentDetached(@NonNull androidx.fragment.app.FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
            }
        }, false);

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
            m_containerViewMap.put(containerViewId, ((Fragment) m_baseObject).getChildFragmentManager());
        }
    }


}

