package com.xuanwenchao.metaphor_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.xuanwenchao.metaphor.Metaphor;
import com.xuanwenchao.metaphor.annotation.MetaphorRes;
import com.xuanwenchao.metaphor.interfaces.IMetaphorManager;
import com.xuanwenchao.metaphor.interfaces.IMetaphorMessage;
import com.xuanwenchao.metaphor_demo.fragments.FragmentA;
import com.xuanwenchao.metaphor_demo.fragments.FragmentB;
import com.xuanwenchao.metaphor_demo.fragments.FragmentC;
import com.xuanwenchao.metaphor_demo.fragments.FragmentX1;
import com.xuanwenchao.metaphor_demo.fragments.FragmentX2;
import com.xuanwenchao.metaphor_demo.fragments.FragmentX3;


/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor_demo
 * @Description: this is a demo of what about Metaphor uses
 * @date May 18,2023
 */

//speecific containers of fragment
@MetaphorRes(containerViewIDs = {R.id.container_number_one, R.id.container_number_two})
public class MainActivity extends AppCompatActivity {

    private static final int CONTAINER_SERIAL_NUMBER_1 = 0;
    private static final int CONTAINER_SERIAL_NUMBER_2 = 1;
    public static final int METAPHOR_MESSAGE_TO_SHOW_FA = 11;
    public static final int METAPHOR_MESSAGE_TO_SHOW_FB = 12;
    public static final int METAPHOR_MESSAGE_TO_SHOW_FC = 13;

    private FragmentX1 m_fx1;
    private FragmentX2 m_fx2;
    private FragmentX3 m_fx3;
    private TextView m_tv_show_fragmentX1;
    private TextView m_tv_show_fragmentX2;
    private TextView m_tv_show_fragmentX3;
    private FragmentA m_fa;
    private FragmentB m_fb;
    private FragmentC m_fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_tv_show_fragmentX1 = findViewById(R.id.tv_show_fragmentX1);
        m_tv_show_fragmentX2 = findViewById(R.id.tv_show_fragmentX2);
        m_tv_show_fragmentX3 = findViewById(R.id.tv_show_fragmentX3);
        initFragments();
        initClickEvent();
    }


    private void initClickEvent() {
        m_tv_show_fragmentX1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after executing initFragment you can switch in anywhere
                Metaphor.with(MainActivity.this).showFragment(m_fx1);
            }
        });

        m_tv_show_fragmentX2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after executing initFragment you can switch in anywhere
                Metaphor.with(MainActivity.this).showFragment(m_fx2);
            }
        });

        m_tv_show_fragmentX3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //after executing initFragment you can switch in anywhere
                Metaphor.with(MainActivity.this).showFragment(m_fx3);
            }
        });
    }

    private void initFragments() {
        m_fx1 = FragmentX1.newInstance();
        m_fx2 = FragmentX2.newInstance();
        m_fx3 = FragmentX3.newInstance();

        IMetaphorManager metaphorManager = Metaphor.with(this);

        //add a set of fragment to specify serial number container (R.id.container_number_two),and show fragment who name is m_fx1
        metaphorManager.addFragment(CONTAINER_SERIAL_NUMBER_2, m_fx1, m_fx2, m_fx3).showFragment(m_fx1);

        m_fa = FragmentA.newInstance();

        //add and show a fragment who name is m_fa to specify serial number container (R.id.container_number_one)
        metaphorManager.addFragment(m_fa, "FA").showFragment("FA");

        metaphorManager.bindListener(new IMetaphorMessage() {
            @Override
            public void OnMessageReceive(Object from, int code, Object msg) {
                if (from == m_fa || from == m_fb || from == m_fc) {
                    switch (code) {
                        case METAPHOR_MESSAGE_TO_SHOW_FA: {
                            metaphorManager.showFragment("FA");
                        }
                        break;

                        case METAPHOR_MESSAGE_TO_SHOW_FB: {
                            if (false == metaphorManager.isTagExist("FB")) {
                                //add fragment dynamically if it is necessary
                                m_fb = FragmentB.newInstance();
                                metaphorManager.addFragment(m_fb, "FB");
                            }
                            metaphorManager.showFragment("FB");
                        }
                        break;

                        case METAPHOR_MESSAGE_TO_SHOW_FC: {
                            if (false == metaphorManager.isTagExist("FC")) {
                                //add fragment dynamically if it is necessary
                                m_fc = FragmentC.newInstance();
                                metaphorManager.addFragment(m_fc, "FC");
                            }
                            metaphorManager.showFragment("FC");
                        }
                        break;

                    }
                }
            }
        });
    }
}