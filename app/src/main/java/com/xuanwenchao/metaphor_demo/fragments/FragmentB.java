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
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentB extends Fragment {
    private TextView m_tv_show_fragmenta;
    private TextView m_tv_show_fragmentc;
    private FragmentC m_fc;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public FragmentB() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentB.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentB newInstance() {
        FragmentB fragment = new FragmentB();
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
        View rootView = inflater.inflate(R.layout.fragment_b, container, false);

        m_tv_show_fragmenta = rootView.findViewById(R.id.tv_show_fragmenta);
        m_tv_show_fragmentc = rootView.findViewById(R.id.tv_show_fragmentc);

        //you can call getManager to get MetaphorManager because it is already created in MainActivity.java
        IMetaphorSubFragmentManager metaphorSubFragmentManager = Metaphor.getManager(FragmentB.this);

        m_tv_show_fragmenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show fragment who tag name is FB
                metaphorSubFragmentManager.showFragment("FA");
            }
        });

        m_tv_show_fragmentc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //there are two ways to implement it
                //first
                metaphorSubFragmentManager.sendMessageToBase(MainActivity.METAPHOR_MESSAGE_TO_SHOW_FC,null);

                //second
                //you must use Metaphor.with(this) to create a new MetaphorManager if you want to add a new subfragment to the container in FragmentB
//                if (false == metaphorSubFragmentManager.isTagExist("FC")) {
//                    //add fragment dynamically if it is necessary
//                    m_fc = FragmentC.newInstance();
//                    metaphorSubFragmentManager.addFragment(m_fc, "FC");
//                }
                //show fragment who tag name is FC
//                metaphorSubFragmentManager.showFragment("FC");

            }
        });

        return rootView;
    }
}