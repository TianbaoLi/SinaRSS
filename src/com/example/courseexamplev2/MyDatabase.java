package com.example.courseexamplev2;

import java.util.ArrayList;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {
	private SQLiteDatabase database;
	private static final String TABLE_NAME = "news",
			TABLE_NAME2 = "offline",
	TITLE_COLUMN = "title",
	CONTENT_COLUMN = "content",
	LINK_COLUMN = "link";
	
	public static final String FILE_PATH = "/database",
			FILE_NAME = "tempdbv2.db";
	
	public MyDatabase(SQLiteDatabase db){
		database = db;
		createTableIfNotExists();
	}
	
	private void createTableIfNotExists(){
		database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+ TITLE_COLUMN + " VARCHAR, " 
            + CONTENT_COLUMN + " VARCHAR, " + LINK_COLUMN + " VARCHAR)" );
		database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + "("+ TITLE_COLUMN + " VARCHAR, " 
	            + CONTENT_COLUMN + " VARCHAR, " + LINK_COLUMN + " VARCHAR)" );
	}
	
	private long insert(String title,String content, String link){
		ContentValues cValues = new ContentValues();
		cValues.put(TITLE_COLUMN, title);
		cValues.put(CONTENT_COLUMN, content);
		cValues.put(LINK_COLUMN, link);
		return database.insert(TABLE_NAME, null, cValues);
	}
	
	private int delete(String link){
		return database.delete(TABLE_NAME, LINK_COLUMN + " = ?", new String[]{link});
	}
	
	private int clearOffline(){
		return database.delete(TABLE_NAME, null, null);
	}
	
	public long insertOffline(String title,String content, String link){
		ContentValues cValues = new ContentValues();
		cValues.put(TITLE_COLUMN, title);
		cValues.put(CONTENT_COLUMN, content);
		cValues.put(LINK_COLUMN, link);
		return database.insert(TABLE_NAME2, null, cValues);
	}
	
	private Cursor query(String link){
		return database.query(TABLE_NAME, null, LINK_COLUMN + " = ?", new String[]{link}, null, null, null);
	}
	
	public boolean collect(News news){
		return collect(news.getTitle(),news.getContent(),news.getUri());
	}
	
	public boolean collect(String title,String content,String link){
		Cursor c = query(link);
		c.moveToNext();
		if(c.isAfterLast()){
			insert(title, content, link);
			return true;
		}
		return false;
	}
	
	public boolean deCollect(String link){
		if(delete(link) == 1)
			return true;
		else return false;
	}
	
	public News[] getAllNewsCollection(){
		return getAllNews(TABLE_NAME);
	}
	
	public News[] getAllNewsOffline(){
		return getAllNews(TABLE_NAME2);
	}
	
	private News[] getAllNews(String tableName){
		Cursor c = database.query(false, tableName, null, null, null, null, null, null, null);
		ArrayList<News> aList = new ArrayList<News>();
		News tempNews;
		for(c.moveToNext();!c.isAfterLast();c.moveToNext()){
			tempNews = new News(c.getString(c.getColumnIndex(TITLE_COLUMN)),
					c.getString(c.getColumnIndex(CONTENT_COLUMN)),
					c.getString(c.getColumnIndex(LINK_COLUMN)));
			aList.add(tempNews);
		}
		return (News[])aList.toArray(new News[0]);
	}
	
	public boolean refreashOffline(News[] arrayNews){
		try{
			clearOffline();
			for(News news:arrayNews){
				insertOffline(news.getTitle(),news.getContent(),news.getUri());
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
