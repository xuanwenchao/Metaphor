package com.xuanwenchao.metaphor_demo.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xuanwenchao.metaphor.Metaphor;
import com.xuanwenchao.metaphor.interfaces.IMetaphorSubFragmentManager;
import com.xuanwenchao.metaphor_demo.MainActivity;
import com.xuanwenchao.metaphor_demo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentA extends Fragment {

    private TextView m_tv_show_fragmentb;
    private FragmentB m_fb;

    public FragmentA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentA.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentA newInstance() {
        FragmentA fragment = new FragmentA();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_a, container, false);
        m_tv_show_fragmentb = rootView.findViewById(R.id.tv_show_fragmentb);
        m_tv_show_fragmentb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMetaphorSubFragmentManager metaphorSubFragmentManager = Metaphor.getManager(FragmentA.this);

                //there are two ways to implement it
                //first
                metaphorSubFragmentManager.sendMessageToBase(MainActivity.METAPHOR_MESSAGE_TO_SHOW_FB,null);


                //second
                //you can call getManager to get MetaphorManager because it is already created in MainActivity.java
                //you must use Metaphor.with(this) to create a new MetaphorManager if you want to add a new subfragment to the container in FragmentA
//                if (false == metaphorSubFragmentManager.isTagExist("FB")) {
//                    //add fragment dynamically if it is necessary
//                    m_fb = FragmentB.newInstance();
//                    metaphorSubFragmentManager.addFragment(m_fb, "FB");
//                }
                //show fragment who tag name is FB
//                metaphorSubFragmentManager.showFragment("FB");

            }
        });
        return rootView;
    }
}