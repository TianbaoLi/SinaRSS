package com.example.sinaRSS;

import java.util.ArrayList;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase {
	private SQLiteDatabase database;
	private static final String TABLE_NAME = "news",//收藏新闻表
	TITLE_COLUMN = "title",//标题
	CONTENT_COLUMN = "content",//内容
	LINK_COLUMN = "link";//链接
	
	public static final String FILE_PATH = "/database",
			FILE_NAME = "newsdb.db";//数据库名
	
	public MyDatabase(SQLiteDatabase db){
		database = db;
		createTableIfNotExists();
	}
	
	private void createTableIfNotExists(){//建表
		database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("+ TITLE_COLUMN + " VARCHAR, " 
            + CONTENT_COLUMN + " VARCHAR, " + LINK_COLUMN + " VARCHAR)" );
	}
	
	private long insert(String title,String content, String link){//插入收藏
		ContentValues cValues = new ContentValues();
		cValues.put(TITLE_COLUMN, title);	
		cValues.put(CONTENT_COLUMN, content);
		cValues.put(LINK_COLUMN, link);
		return database.insert(TABLE_NAME, null, cValues);
	}
	
	private int delete(String link){//删除收藏
		return database.delete(TABLE_NAME, LINK_COLUMN + " = ?", new String[]{link});
	}
	
	private Cursor query(String link){//按连接查询
		return database.query(TABLE_NAME, null, LINK_COLUMN + " = ?", new String[]{link}, null, null, null);
	}
	
	public boolean collect(News news){//检测是否收藏，未收藏则添加
		return collect(news.getTitle(),news.getContent(),news.getUri());
	}
	
	public boolean collect(String title,String content,String link){//检测是否收藏，未收藏则添加
		Cursor c = query(link);
		c.moveToNext();
		if(c.isAfterLast()){
			insert(title, content, link);
			return true;
		}
		return false;
	}
	
	public boolean deCollect(String link){//取消收藏
		if(delete(link) == 1)
			return true;
		else return false;
	}
	
	public News[] getAllNewsCollection(){//返回收藏列表
		return getAllNews(TABLE_NAME);
	}
	
	private News[] getAllNews(String tableName){//返回tableName中所有新闻信息
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

}
