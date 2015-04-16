package com.example.courseexamplev2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class OnlineTabFragment extends Fragment {

	MyScrollView sv;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.online_tab, null); 
        sv = (MyScrollView)view.findViewById(R.id.sv);
        
        NetworkAsyncTask nat = new NetworkAsyncTask(sv, (MainActivity)getActivity(), inflater);
        nat.execute();
        
        return view;  
    } 
}
