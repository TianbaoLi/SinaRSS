package com.example.sinaRSS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.integer;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.Toast;

public class NetworkAsyncTask extends AsyncTask<Integer, Integer, News[]> {
	private MyScrollView myScrollView = null;
	private MainActivity activity = null;
	private LayoutInflater inflater = null;
	private HttpClient hClient = null;
	private static String url = "http://rss.sina.com.cn/news/marquee/ddt.xml";//RSS源地址
	private int mode;
	
	public NetworkAsyncTask(MyScrollView msv,MainActivity act,LayoutInflater li){
		super();
		myScrollView = msv;
		activity = act;
		inflater = li;
		mode = 0;
	}
	
	public static void setUrl(int index)
	{
		switch(index)
		{
			case 0://新闻要闻
				url="http://rss.sina.com.cn/news/marquee/ddt.xml";
				break;
			case 1://国内新闻
				url="http://rss.sina.com.cn/news/china/focus15.xml";
				break;
			case 2://国际新闻
				url="http://rss.sina.com.cn/news/world/focus15.xml";
				break;
			case 3://体育新闻
				url="http://rss.sina.com.cn/news/allnews/sports.xml";
				break;
			case 4://军事新闻
				url="http://rss.sina.com.cn/jczs/focus.xml";
				break;
			case 5://娱乐新闻
				url="http://rss.sina.com.cn/news/allnews/ent.xml";
				break;
			case 6://教育新闻
				url="http://rss.sina.com.cn/edu/focus19.xml";
				break;
			default://新闻要闻
				url="http://rss.sina.com.cn/news/marquee/ddt.xml";
				break;
		}
	}

	@Override
	protected News[] doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		InputStream is = null;
		News[] results;
		ArrayList<News> alResultNews = new ArrayList<News>();
		try {
			hClient = new DefaultHttpClient();
			HttpGet hGet = new HttpGet(url);
			HttpResponse hResponse = hClient.execute(hGet);
			HttpEntity hEntity = hResponse.getEntity();
			is = hEntity.getContent();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(is,"utf-8"));
			StringBuilder sBuilder = new StringBuilder();
			
	        String line = null;
			while ((line = bReader.readLine()) != null) {//通过HTTP请求读取
				if(line.indexOf("<item>") == -1)
					continue;
				String titleString,contentString,urlString,itemString = null;
				while(line.indexOf("</item>") == -1){
					itemString += line;
					line = bReader.readLine();
				}
				titleString = itemString.substring(itemString.indexOf("<title>")+"<title>".length(),itemString.indexOf("</title>"));//整理新闻格式
				titleString = titleString.substring(titleString.indexOf("CDATA[")+"CDATA[".length(),titleString.lastIndexOf("]") - 1);
				contentString = itemString.substring(itemString.indexOf("<description>")+"<description>".length(),itemString.indexOf("</description>"));
				contentString = contentString.substring(contentString.indexOf("CDATA[")+"CDATA[".length(),contentString.lastIndexOf("]") - 1);
				urlString = itemString.substring(itemString.indexOf("<link>")+"<link>".length(),itemString.indexOf("</link>"));
				urlString = urlString.substring(urlString.indexOf("url=")+"url=".length());
				News news = new News(titleString, contentString, urlString);
				alResultNews.add(news);
	    }
	        is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			alResultNews.add(new News("network failed.", "network failed.", ""));
			e.printStackTrace();
		}
		results = new News[alResultNews.size()];
		Iterator<News> iterator = alResultNews.iterator();
		for(int i = 0;i < results.length;++i){
			results[i] = iterator.next();
		}
		return results;
	}
	
	@Override
	protected void onPostExecute(News[] results){
		if(mode == 0){
			myScrollView.setActivity(activity);
			myScrollView.setInflater(inflater);
			for (News news : results) {
				myScrollView.addNews(news,0);
			}
		}
	}

}
