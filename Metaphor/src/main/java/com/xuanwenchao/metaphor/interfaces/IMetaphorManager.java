package com.xuanwenchao.metaphor.interfaces;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.interfaces
 * @Description: as a manager interface in public for all class, you can use it by chain operation
 * @date Dec 02,2022
 */

public interface IMetaphorManager {

    /**
     * @Description: bind a listener for communication between base fragment and sub fragment
     * @param metaphorMessage listener for message
     * @return: void
     */
    public IMetaphorManager bindListener(IMetaphorMessage metaphorMessage);

    /**
     * @Description: Add container view ID for show fragment, same as MetaphorRes in effect
     * @containerViewId container view is use to show fragment
     * @return: void
     */
    public void addContainerViewID(@IdRes int containerViewId);


    /**
     * @param fragment it will be add to first containerViewId
     * @return IMetaphorManager
     * @Description: add fragment into stack list, the container view of the fragment is the first containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(@NonNull Fragment fragment);


    /**
     * @param fragment it will be add to first containerViewId
     * @param strTag   tag name of fragment
     * @return IMetaphorManager
     * @Description: add fragment into stack list, the container view of the fragment is the first containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(@NonNull Fragment fragment, @NonNull String strTag);

    /**
     * @param containerViewId container view is use to show fragment
     * @param fragment        it will be add to containerViewId
     * @param strTag          tag name of fragment
     * @Description: add fragment to FragmentManager for containerViewId and hide
     * @return: IMetaphorManager
     */
    public IMetaphorManager addFragment(@IdRes int containerViewId, @NonNull Fragment fragment, @NonNull String strTag);

    /**
     * @param fragments the list will be add to first containerViewId
     * @return IMetaphorManager
     * @Description: add fragment list into stack list, the container view of the fragment is the first containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(@NonNull Fragment... fragments);


    /**
     * @param fragments the list will be add to specify Serial Number containerViewId
     * @return IMetaphorManager
     * @Description: add fragment list into stack list, the container view of the fragment is the specify Serial Number containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(int containerViewSerialNumber, @NonNull Fragment... fragments);

    /**
     * @param strTags   the tag list for fragments list
     * @param fragments the list will be add to first containerViewId
     * @return IMetaphorManager
     * @Description: add fragment list and tag list into stack list, the container view of the fragment is the first containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(@NonNull String[] strTags, @NonNull Fragment... fragments);


    /**
     * @param strTags   the tag list for fragments list
     * @param fragments the list will be add to specify Serial Number containerViewId
     * @return IMetaphorManager
     * @Description: add fragment list and tag list into stack list, the container view of the fragment is the specify Serial Number containerView,
     * throw exception if resList's size is zero
     */
    public IMetaphorManager addFragment(int containerViewSerialNumber,@NonNull String[] strTags, @NonNull Fragment... fragments);

    /**
     * @param fragment To be show object
     * @Description: Show fragment and hide others that is contained in stack list, it will be added when it not existed.
     * the container view of the fragment is the first containerView in resList, throw exception if resList's size is zero.
     * @return: IMetaphorManager
     */
    public IMetaphorManager showFragment(@NonNull Fragment fragment);

    /**
     * @param strTag tag name of fragment
     * @Description: show fragment on container view that tag name is strTag
     * @return: IMetaphorManager
     */
    public IMetaphorManager showFragment(@NonNull String strTag);

    /**
     * @param fragment To be hide object
     * @Description: Hide fragment whitch is contained in stack list
     * @return: void
     */
    void hideFragment(@NonNull Fragment fragment);

    /**
     * @param strTag tag name of fragment
     * @Description: Hide fragment on container view that tag name is strTag
     * @return: void
     */
    void hideFragment(@NonNull String strTag);

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
     * @param strTag   tag name of fragment
     * @return void
     * @Description:  Find a fragment tag name in the stack of MetaphorManager
     */
     boolean isTagExist(@NonNull String strTag);

}
