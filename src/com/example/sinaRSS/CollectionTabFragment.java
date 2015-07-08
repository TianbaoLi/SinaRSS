package com.example.sinaRSS;

import java.io.File;

import com.example.sinaRSS.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


@SuppressLint("NewApi")
public class CollectionTabFragment extends Fragment {
	
	private MyScrollView myScrollView;
	private MyDatabase myDatabase;
	private MainActivity activity;
	private final String DATABASE_PATH = "/database", DATABASE_FILENAME = "examplev2.db";
	
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View view = inflater.inflate(R.layout.collection_tab, null); 
        myScrollView = (MyScrollView)view.findViewById(R.id.sv);
        activity = (MainActivity)getActivity();
        myDatabase = activity.getMyDatabase();
        
        News[] arrayNews = myDatabase.getAllNewsCollection();//返回数据库中收藏新闻列表
        
        myScrollView.setActivity((MainActivity)getActivity());
		myScrollView.setInflater(inflater);
        for (News news : arrayNews){//逐条添加至页面
        	myScrollView.addNews(news, 1);
        }
        
        return view;  
    } 

}
