package com.example.courseexamplev2;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class OfflineTabFragment extends Fragment {

	MyScrollView myScrollView;
	Button btnRefresh;
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.offline_tab, null); 
        myScrollView = (MyScrollView)view.findViewById(R.id.sv);
        myScrollView.setActivity((MainActivity)getActivity());
        myScrollView.setInflater(inflater);
        
        MyDatabase myDatabase = ((MainActivity)getActivity()).getMyDatabase();
        News[] arrayNews = myDatabase.getAllNewsOffline();
        for(News news:arrayNews){
        	myScrollView.addNews(news, 0);
        }
        
        if(arrayNews.length == 0){
        	myScrollView.addNews(new News("Nothing Downloaded.", "Nothing Yet.", null), 2);
        }
        
        
        btnRefresh = (Button)view.findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				getActivity().startService(new Intent("com.example.CourseExampleV2.DownloadService"));
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Start Download.", Toast.LENGTH_LONG).show();
			}
		});
        
        return view;  
    } 

}
