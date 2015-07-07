package com.example.sinaRSS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.example.sinaRSS.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyScrollView extends LinearLayout {
	private MainActivity activity = null;
	private LayoutInflater inflater = null;
	private ArrayList<News> alNews = new ArrayList<News>();
	private SQLiteDatabase sqldb = null;
	
	//add button
	private Button button;
	
	private final String clmTitle = "title", clmContent = "content", clmLink = "link", tableName = "news";

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyScrollView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	
	@SuppressLint("NewApi")
	public MyScrollView(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
	}
	
	public int addNews(final News news,int mode){//添加一条新闻数据
		if(mode != 0 && mode != 1 && mode != 2)
			return 3;
		if(inflater == null)
			return 1;
		alNews.add(news);
		View tempView = inflater.inflate(R.layout.news_model, null);
		TextView tvTitle = (TextView)tempView.findViewById(R.id.tvTitle);
		TextView tvContent = (TextView)tempView.findViewById(R.id.tvContent);
		button=(Button)tempView.findViewById(R.id.tvadd);
		tvTitle.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvTitle.setTextColor(Color.rgb(67, 13, 253)); 
		tvTitle.setText(news.getTitle());
		tvContent.setText(news.getContent());
		
		//add button......................................
		
		button.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyDatabase myDatabase = activity.getMyDatabase();
				myDatabase.collect(news);
			}
		});
		
		tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String link = getLink(((TextView)v).getText().toString());
				if(link == null)
					return;
				Uri uri = Uri.parse(link);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				activity.startActivity(intent);
			}
		});
		
		if(mode == 0){//online/offline mode
			tvTitle.setOnLongClickListener(new Mode0LongClickListener());
			tvContent.setOnLongClickListener(new Mode0LongClickListener());
		}else if(mode == 1){//collection mode
			tvTitle.setOnLongClickListener(new Mode1LongClickListener());
			tvContent.setOnLongClickListener(new Mode1LongClickListener());
		}else if(mode == 2){
			//nothing
		}
		
		addView(tempView);
		return 0;
	}
	
	public void setInflater(LayoutInflater li){
		inflater = li;
	}
	
	public void setActivity(MainActivity act){
		activity = act;
	}
	
	public void setSQLiteDatabase(SQLiteDatabase sd){
		sqldb = sd;
		
	}
	
	private News getNews(String str){
		Iterator<News> iterator = alNews.iterator();
		String link = null;
		News tempNews;
		while(iterator.hasNext()){
			tempNews = iterator.next();
			if(tempNews.getTitle() == str || tempNews.getContent() == str){
				return tempNews;
			}
		}
		return null;
	}
	
	private String getLink(String str){
		News news = getNews(str);
		if(news == null) return null;
		else return news.getUri();
	}
	
	private int popToast(String str){
		if(activity == null)
			return -1;
		Toast.makeText(activity.getApplicationContext(), str, Toast.LENGTH_LONG);
		return 0;
	}
	
	class Mode0LongClickListener implements OnLongClickListener{

		private View view;
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			view = v;
			Dialog dialog = new AlertDialog.Builder(activity)
	        .setTitle("Noticement")
	        .setMessage("ȷ���ղ���������?")
	        //.setIcon(R.drawable.quit)
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        //enter button event
	        	if(sqldb == null)
	        		popToast("null SQLiteDatabase");
	        	MyDatabase myDatabase = activity.getMyDatabase();
	        	if(myDatabase.collect(getNews(((TextView)view).getText().toString())))
	        		popToast("Collection successed.");
	        	else
	        		popToast("The news is in the collection.");
	        }
	        })
	        .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	         //cancel button event
	        }
	        })
	        .show();
			return false;
		}
		
	}
	
	class Mode1LongClickListener implements OnLongClickListener{

		private View view;
		
		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			view = v;
			Dialog dialog = new AlertDialog.Builder(activity)
	        .setTitle("Noticement")
	        .setMessage("ȷ�����ղؼ�ɾ����������?")
	        //.setIcon(R.drawable.quit)
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	        //enter button event
	        	if(sqldb == null)
	        		popToast("null SQLiteDatabase");
	        	MyDatabase myDatabase = activity.getMyDatabase();
	        	News tempNews = getNews(((TextView)view).getText().toString());
	        	if(!myDatabase.deCollect(tempNews.getUri()))
	        		popToast("wrong number of delete operation.");
	        	else
	        		popToast("Delete successed.");
	        }
	        })
	        .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	         //cancel button event
	        }
	        })
	        .show();
			return false;
		}
		
	}
}
