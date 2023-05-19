package com.xuanwenchao.metaphor;

import androidx.fragment.app.Fragment;

import com.xuanwenchao.metaphor.interfaces.IMetaphorManager;
import com.xuanwenchao.metaphor.interfaces.IMetaphorSubFragmentManager;
import com.xuanwenchao.metaphor.manager.MetaphorManager;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor
 * @Description: Metaphor is a operator witch face to user
 * @date Dec 21,2022
 */

public class Metaphor {

    /**
     * @param object It is a container class that is used for show fragment and only is object what is kind of AppCompatActivity or Fragment.
     * @Description: get IMetaphorManager object, it will be create if it not existed.
     * for details you can visit interface define of IMetaphorManager
     * @return: IMetaphorManager
     **/
    public static IMetaphorManager with(Object object) {
        return MetaphorManager.createMetaphorManager(object);
    }

    /**
     * @param object It is a fragment that has been added to container
     * @Description: get IMetaphorSubFragmentManager object, it will be create if it not existed.
     * user can do more action with IMetaphorSubFragmentManager.
     * @return: IMetaphorSubFragmentManager
     **/
    public static IMetaphorSubFragmentManager getManager(Fragment object) {
        return MetaphorManager.findMetaphorManager(object);
    }


    /**
     * @param b log switch variant, default is false
     * @Description: you can set output log of metaphor to logcat console.
     * @return: Void
     **/
    public static void setShowDebugLog(boolean b) {
        MetaphorManager.isPrintLog = b;
    }
}
