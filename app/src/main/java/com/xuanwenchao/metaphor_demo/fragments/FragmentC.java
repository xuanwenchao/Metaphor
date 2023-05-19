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
 * Use the {@link FragmentC#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentC extends Fragment {
    private TextView m_tv_show_fragmenta;
    private TextView m_tv_show_fragmentb;

    public FragmentC() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentC.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentC newInstance() {
        FragmentC fragment = new FragmentC();

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
        View rootView = inflater.inflate(R.layout.fragment_c, container, false);

        m_tv_show_fragmenta = rootView.findViewById(R.id.tv_show_fragmenta);
        m_tv_show_fragmentb = rootView.findViewById(R.id.tv_show_fragmentb);

        //you can call getManager to get MetaphorManager because it is already created in MainActivity.java
        IMetaphorSubFragmentManager metaphorSubFragmentManager = Metaphor.getManager(FragmentC.this);

        m_tv_show_fragmenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //there are two ways to implement it
                //first
                metaphorSubFragmentManager.sendMessageToBase(MainActivity.METAPHOR_MESSAGE_TO_SHOW_FA,null);

                //second
                //show fragment who tag name is FB
//                metaphorSubFragmentManager.showFragment("FA");
            }
        });

        m_tv_show_fragmentb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //there are two ways to implement it
                //first
//                metaphorSubFragmentManager.sendMessageToBase(MainActivity.METAPHOR_MESSAGE_TO_SHOW_FB,null);

                //second
                //show fragment who tag name is FB
                metaphorSubFragmentManager.showFragment("FB");

            }
        });

        return rootView;
    }
}