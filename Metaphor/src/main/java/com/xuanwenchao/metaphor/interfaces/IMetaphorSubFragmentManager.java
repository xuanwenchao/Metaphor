package com.xuanwenchao.metaphor.interfaces;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.interfaces
 * @Description: define the interface for sub fragment that has been added to fragmentmanager
 * @date Dec 08,2022
 */
public interface IMetaphorSubFragmentManager {
    /**
     * @param code it is a code for custom define
     * @param msg  it is a object for custom define
     * @Description: send message to base object
     * @return: IMetaphorManager
     */
    public IMetaphorSubFragmentManager sendMessageToBase(int code, Object msg);

    /**
     * @param metaphorMessage listener for message
     * @Description: bind a listener for communication between base fragment and sub fragment
     * @return: void
     */
    void bindFragmentListener(IMetaphorMessage metaphorMessage);

    /**
     * @param tagForFragment tag name of fragment
     * @Description: send message to another fragment
     * @return: void
     */
    void sendMessageToFragment(String tagForFragment, int code, Object msg);

    /**
     * @param fragment target fragment
     * @Description: send message to another fragment
     * @return: void
     */
    void sendMessageToFragment(Fragment fragment, int code, Object msg);

    /**
     * @param strTag tag name of fragment
     * @Description: show fragment on container view that tag name is strTag
     * @return: IMetaphorSubFragmentManager
     */
    public IMetaphorSubFragmentManager showFragment(@NonNull String strTag);

    /**
     * @param fragment To be show object
     * @Description: Show fragment and hide others that is contained in stack list, it will be added when it not existed.
     * the container view of the fragment is the first containerView in resList, throw exception if resList's size is zero.
     * @return: IMetaphorSubFragmentManager
     */
    public IMetaphorSubFragmentManager showFragment(@NonNull Fragment fragment);

    /**
     * @param fragment it will be add to first containerViewId
     * @param strTag   tag name of fragment
     * @return void
     * @Description: add fragment into stack list, the container view of the fragment is the first containerView,
     * throw exception if resList's size is zero
     */
    public void addFragment(@NonNull Fragment fragment, @NonNull String strTag);

    /**
     * @param strTag   tag name of fragment
     * @return void
     * @Description:  Find a fragment tag name in the stack of MetaphorManager
     */
    boolean isTagExist(@NonNull String strTag);
}
