package com.xuanwenchao.metaphor.manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xuanwenchao.metaphor.interfaces.IMetaphorMessage;
import com.xuanwenchao.metaphor.interfaces.IMetaphorSubFragmentManager;


/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.manager
 * @Description: In order to action for show or hide in chain operate
 * @date Dec 06,2022
 */
public class MetaphorSubFragmentManager implements IMetaphorSubFragmentManager {

    public static IMetaphorSubFragmentManager create(MetaphorManager metaphorManager, Fragment fragment) {
        return new MetaphorSubFragmentManager(metaphorManager, fragment);
    }

    private MetaphorManager m_metaphorManager;
    private Fragment m_fragment;

    MetaphorSubFragmentManager(MetaphorManager metaphorManager, Fragment fragment) {
        m_metaphorManager = metaphorManager;
        m_fragment = fragment;
    }

    @Override
    public IMetaphorSubFragmentManager sendMessageToBase(int code, Object msg) {
        m_metaphorManager.sendMessageToBase(code, msg);
        return this;
    }

    @Override
    public void bindFragmentListener(IMetaphorMessage metaphorMessage) {
        m_metaphorManager.bindFragmentListener(metaphorMessage);
    }

    @Override
    public void sendMessageToFragment(String tagForFragment, int code, Object msg) {
        m_metaphorManager.sendMessageToFragment(tagForFragment, code, msg);
    }

    @Override
    public void sendMessageToFragment(Fragment fragment, int code, Object msg) {
        m_metaphorManager.sendMessageToFragment(fragment, code, msg);
    }

    @Override
    public IMetaphorSubFragmentManager showFragment(@NonNull String strTag) {
        m_metaphorManager.showFragment(strTag);
        return this;
    }

    @Override
    public IMetaphorSubFragmentManager showFragment(@NonNull Fragment fragment) {
        m_metaphorManager.showFragment(fragment);
        return this;
    }

    @Override
    public void addFragment(@NonNull Fragment fragment, @NonNull String strTag) {
        m_metaphorManager.addFragment(fragment, strTag);
    }

    @Override
    public boolean isTagExist(@NonNull String strTag) {
        return m_metaphorManager.isTagExist(strTag);
    }
}
