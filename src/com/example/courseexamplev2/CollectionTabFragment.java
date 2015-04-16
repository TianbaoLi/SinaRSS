package com.example.courseexamplev2;

import java.io.File;

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
        
        News[] arrayNews = myDatabase.getAllNewsCollection();
        
        myScrollView.setActivity((MainActivity)getActivity());
		myScrollView.setInflater(inflater);
        for (News news : arrayNews){
        	myScrollView.addNews(news, 1);
        }
        
        if(arrayNews.length == 0)
        	myScrollView.addNews(new News("nothing in collection", "nothing yet", null), 2);
        
        return view;  
    } 

}
