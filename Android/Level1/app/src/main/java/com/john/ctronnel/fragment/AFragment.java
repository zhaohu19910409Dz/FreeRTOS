package com.john.ctronnel.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.john.ctronnel.R;

public class AFragment extends Fragment {

    private TextView tv;
    private Activity activity;
    private Button change,reset, mBtnMesg;
    BFragment bFragment = null;
    private IOnMessageClick listener;

    public static AFragment newInstance(String title)
    {
        AFragment fragment = new AFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragmeng, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        Log.d("AFragment", "---onCreateView---");
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv = (TextView)view.findViewById(R.id.tv1);
        if(getActivity() != null)
        {

        }
        else
        {

        }

        if(getArguments() != null)
        {
            tv.setText(getArguments().getString("Title"));
        }

        change = (Button)view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bFragment == null)
                {
                    bFragment = new BFragment();
                }
                Fragment fragment = getFragmentManager().findFragmentByTag("TagA");
                if(fragment != null)
                {
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fg_continer, bFragment).addToBackStack(null).commitAllowingStateLoss();
                }
                else
                {
                    getFragmentManager().beginTransaction().replace(R.id.fg_continer, bFragment).addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });
        reset = (Button)view.findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("我是新文字");
            }
        });

        mBtnMesg = (Button)view.findViewById(R.id.message);
        mBtnMesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((ContinerActivity)getActivity()).setData("I am John");
                listener.onClick("I am better man");
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //activity = (Activity)context;
        try {
            listener = (IOnMessageClick) context;
        }catch (ClassCastException e)
        {
            throw  new ClassCastException("Activity must implement IOnClick interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //cancle nosyc task
    }

    public interface IOnMessageClick
    {
        void onClick(String text);
    }
}
