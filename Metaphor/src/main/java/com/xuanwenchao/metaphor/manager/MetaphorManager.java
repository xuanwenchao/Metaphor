package com.xuanwenchao.metaphor.manager;

import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xuanwenchao.metaphor.annotation.MetaphorRes;
import com.xuanwenchao.metaphor.exception.MetaphorException;
import com.xuanwenchao.metaphor.interfaces.IMetaphorManager;
import com.xuanwenchao.metaphor.interfaces.IMetaphorMessage;
import com.xuanwenchao.metaphor.interfaces.IMetaphorSubFragmentManager;

import java.util.HashMap;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.manager
 * @Description: base function had been supplied implements IMetaphorManager, optional strategy class
 * @date Dec 03,2022
 */
public abstract class MetaphorManager implements IMetaphorManager {

    static final String kErrorTag = "Metaphor Error:";
    public static boolean isPrintLog = true;


    // hashcode of base object : MetaphorManager object
    static SparseArray m_metaphorManagers = new SparseArray<IMetaphorManager>();

    //map is (Hash Code of fragment address) : (IMetaphorMessage)
    SparseArray m_fragmentToListener = new SparseArray<IMetaphorMessage>();

    //map is (container view ID) : (FragmentManager)
    SparseArray m_containerViewMap = new SparseArray<androidx.fragment.app.FragmentManager>();

    //map is (Hash Code of fragment address) : (container view ID)
    SparseArray m_fragmentToContainerView = new SparseArray<Integer>();

    //map is (Tag String of fragment) : (container view ID)
    HashMap<String, Fragment> m_tagToFragment = new HashMap<>();

    int[] m_containerViewIds;
    Object m_baseObject = null;
    IMetaphorMessage m_metaphorMessage;
    Fragment m_currentSubFragment; //the fragment that called findMetaphorManager

    public static IMetaphorManager createMetaphorManager(@NonNull Object object) {
        int objectCode = System.identityHashCode(object);
        IMetaphorManager iMetaphorManager = (IMetaphorManager) m_metaphorManagers.get(objectCode);
        if (iMetaphorManager != null) {
            return iMetaphorManager;
        }
        if (object instanceof AppCompatActivity) {
            MetaphorActivityManager metaphorManager = MetaphorActivityManager.createActivityManager((AppCompatActivity) object);
            m_metaphorManagers.put(objectCode, metaphorManager);
            return metaphorManager;
        } else if (object instanceof Fragment) {
            IMetaphorManager metaphorManager = MetaphorFragmentManager.createFragmentManager((Fragment) object);
            m_metaphorManagers.put(objectCode, metaphorManager);
            return metaphorManager;
        } else {
            throw new MetaphorException("Base class must used on AppCompatActivity or Fragment");
        }
    }

    /**
     * @param object It is a fragment that has been added to MetaphorManager
     * @Description: Find the relative MetaphorManager by sub fragment
     * @return: MetaphorManager or null if not exist
     **/
    public static IMetaphorSubFragmentManager findMetaphorManager(@NonNull Fragment object) {
        for (int i = 0; i < m_metaphorManagers.size(); i++) {
            MetaphorManager metaphorManager = (MetaphorManager) (m_metaphorManagers.valueAt(i));
            int objectCode = System.identityHashCode(object);
            if (metaphorManager.isContainFragmentCode(objectCode)) {
                metaphorManager.m_currentSubFragment = object;
                return MetaphorSubFragmentManager.create(metaphorManager, (Fragment) object);
            }
        }
        return null;
    }


    /**
     * @Description: Retrieve the resource configuration with annotation
     * @return: void
     */
    MetaphorManager(@NonNull Object object) {
        Class<?> clazz = object.getClass();
        MetaphorRes metaphorRes = clazz.getAnnotation(MetaphorRes.class);
        int[] cvIDs = metaphorRes.containerViewIDs();
        if (cvIDs.length > 0) {
            m_containerViewIds = cvIDs;
        }
    }

    /**
     * @Description: Release static space (m_metaphorManagers) after object is destroy.
     * @return: void
     */
    void OnMetaphorManagerDestroy() {
        if (m_baseObject != null) {
            int objectCode = System.identityHashCode(m_baseObject);
            m_metaphorManagers.remove(objectCode);
            m_baseObject = null;
        }
    }


    @Override
    public IMetaphorManager bindListener(IMetaphorMessage metaphorMessage) {
        m_metaphorMessage = metaphorMessage;
        return this;
    }

    void sendMessageToBase(int code, Object msg) {
        if (m_metaphorMessage != null) {
            m_metaphorMessage.OnMessageReceive(m_currentSubFragment, code, msg);
        } else {
            printErrorLog("sendMessageToBase -> code:" + code + " msg:" + msg.toString() + ": base is not bind listener");
        }
    }

    void bindFragmentListener(IMetaphorMessage metaphorMessage) {
        if (m_currentSubFragment != null && metaphorMessage != null) {
            int fragmentCode = System.identityHashCode(m_currentSubFragment);
            m_fragmentToListener.put(fragmentCode, metaphorMessage);
        } else {
            printErrorLog("bindFragmentListener -> m_currentSubFragment=" + m_currentSubFragment + ",metaphorMessage=" + metaphorMessage);
        }
    }

    public void sendMessageToFragment(String tagForFragment, int code, Object msg) {
        if (tagForFragment != null) {
            Fragment fragment = m_tagToFragment.get(tagForFragment);
            if (fragment != null) {
                sendMessageToFragment(fragment, code, msg);
            } else {
                printErrorLog("sendMessageToFragment -> tag name:" + tagForFragment + " could not be found.");
            }
        } else {
            printErrorLog("sendMessageToFragment -> tag is null");
        }
    }

    public void sendMessageToFragment(Fragment fragment, int code, Object msg) {
        int fragmentCode = System.identityHashCode(fragment);
        Object iMetaphorMessage = m_fragmentToListener.get(fragmentCode, null);
        if (iMetaphorMessage != null) {
            if (m_currentSubFragment != null) {
                ((IMetaphorMessage) iMetaphorMessage).OnMessageReceive(m_currentSubFragment, code, msg);
            } else {
                ((IMetaphorMessage) iMetaphorMessage).OnMessageReceive(m_baseObject, code, msg);
            }
        } else {
            printErrorLog("sendMessageToFragment -> Fragment:" + fragment + ": is not bind listener");
        }
    }

    void printErrorLog(String strMessage) {
        if (isPrintLog) {
            Log.e(kErrorTag, strMessage);
        }
    }

    boolean isContainFragmentCode(int fragmentCode) {
        Object obj = m_fragmentToContainerView.get(fragmentCode);
        return obj != null;
    }

    @Override
    public IMetaphorManager addFragment(@NonNull Fragment fragment) {
        return this.addFragment(fragment, null);
    }

    @Override
    public IMetaphorManager addFragment(@NonNull Fragment fragment, @NonNull String strTag) {
        if (m_containerViewMap.size() <= 0) {
            throw new MetaphorException("No container view ID be specified! 22178!");
        }

        Integer key = m_containerViewMap.keyAt(0);
        return this.addFragment(key, fragment, strTag);
    }

    @Override
    public IMetaphorManager addFragment(int containerViewId, @NonNull Fragment fragment, @NonNull String strTag) {
        this.addContainerViewID(containerViewId);

        int fragmentCode = System.identityHashCode(fragment);
        Integer cvIDexist = (Integer) m_fragmentToContainerView.get(fragmentCode);
        if (cvIDexist != null) {
            //The fragment will be remove from FragmentManager if existed.
            ((androidx.fragment.app.FragmentManager) m_containerViewMap.get(containerViewId)).beginTransaction().remove(fragment);
            m_fragmentToContainerView.delete(fragmentCode);
        }
        m_fragmentToContainerView.put(fragmentCode, containerViewId);

        FragmentTransaction fragmentTransaction = ((FragmentManager) m_containerViewMap.get(containerViewId)).beginTransaction();
        if (strTag == null || strTag.length() == 0) {
            fragmentTransaction.add(containerViewId, fragment).hide(fragment);
        } else {
            m_tagToFragment.remove(strTag);
            m_tagToFragment.put(strTag, fragment);
            fragmentTransaction.add(containerViewId, fragment, strTag).hide(fragment);
        }
        fragmentTransaction.commitNowAllowingStateLoss();
        return this;
    }


    @Override
    public IMetaphorManager addFragment(@NonNull Fragment... fragments) {
        return this.addFragment(null, fragments);
    }

    @Override
    public IMetaphorManager addFragment(int containerViewSerialNumber, @NonNull Fragment... fragments) {
        return this.addFragment(containerViewSerialNumber, null, fragments);
    }

    @Override
    public IMetaphorManager addFragment(@NonNull String[] strTags, @NonNull Fragment... fragments) {
        return this.addFragment(0, strTags, fragments);
    }

    @Override
    public IMetaphorManager addFragment(int containerViewSerialNumber, @NonNull String[] strTags, @NonNull Fragment... fragments) {
        if (m_containerViewMap.size() <= containerViewSerialNumber) {
            throw new MetaphorException("No container view ID be specified! 22220!");
        }
        Integer cvID = m_containerViewMap.keyAt(containerViewSerialNumber);
        androidx.fragment.app.FragmentManager fmg = (androidx.fragment.app.FragmentManager) m_containerViewMap.valueAt(containerViewSerialNumber);

        FragmentTransaction ft = fmg.beginTransaction();
        int i = 0;

        for (Fragment f : fragments) {
            if (strTags != null && strTags.length > i) {
                m_tagToFragment.remove(strTags[i]);
                m_tagToFragment.put(strTags[i], f);
                ft.add(cvID, f, strTags[i]).hide(f);
            } else {
                ft.add(cvID, f).hide(f);
            }
            int fragmentCode = System.identityHashCode(f);
            Integer cvIDexist = (Integer) m_fragmentToContainerView.get(fragmentCode);
            if (cvIDexist != null) {
                //The fragment will be remove from FragmentManager if existed.
                ft.remove(f);
                m_fragmentToContainerView.delete(fragmentCode);
            }
            m_fragmentToContainerView.put(fragmentCode, cvID);
            i++;
        }
        ft.commit();
//        ft.commitNowAllowingStateLoss();
        return this;
    }


    @Override
    public IMetaphorManager showFragment(@NonNull Fragment fragment) {
        int fragmentCode = System.identityHashCode(fragment);
        Integer cvIDexist = (Integer) m_fragmentToContainerView.get(fragmentCode);
        if (cvIDexist != null) {
            androidx.fragment.app.FragmentManager fragmentManager = ((androidx.fragment.app.FragmentManager) m_containerViewMap.get(cvIDexist));
            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (Fragment f : fragmentManager.getFragments()) {
                int fc = System.identityHashCode(f);
                Integer cv = (Integer) m_fragmentToContainerView.get(fc);
                if (cv.intValue() == cvIDexist.intValue() && f != fragment) {
                    ft.hide(f);
                }
            }
            ft.show(fragment);//.addToBackStack(null);

            ft.commit();
//            ft.commitAllowingStateLoss();
            int a = 10;
        } else {
            throw new MetaphorException("No container view ID be found! 221266!");
        }
        return this;
    }

    @Override
    public IMetaphorManager showFragment(@NonNull String strTag) {
        Fragment fragment = m_tagToFragment.get(strTag);
        if (fragment != null) {
            this.showFragment(fragment);
        } else {
            printErrorLog("showFragment -> strTag:" + strTag + ": is not exist");
        }
        return this;
    }

    @Override
    public void hideFragment(@NonNull Fragment fragment) {
        int fragmentCode = System.identityHashCode(fragment);
        Integer cvIDexist = (Integer) m_fragmentToContainerView.get(fragmentCode);
        if (cvIDexist != null) {
            androidx.fragment.app.FragmentManager fragmentManager = ((androidx.fragment.app.FragmentManager) m_containerViewMap.get(cvIDexist));
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(fragment);
            ft.commit();
        } else {
            throw new MetaphorException("No container view ID be found! 221290!");
        }
    }

    @Override
    public void hideFragment(@NonNull String strTag) {
        Fragment fragment = m_tagToFragment.get(strTag);
        if (fragment != null) {
            this.hideFragment(fragment);
        } else {
            printErrorLog("hideFragment -> strTag:" + strTag + ": is not exist");
        }
    }

    @Override
    public boolean isTagExist(@NonNull String strTag) {
        Fragment fragment = m_tagToFragment.get(strTag);
        return fragment != null ? true : false;
    }

}

