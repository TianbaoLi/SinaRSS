package com.example.courseexamplev2;

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
	private String url = "http://rss.sina.com.cn/news/marquee/ddt.xml";
	private int mode;
	private DownloadService service = null;
	
	public NetworkAsyncTask(MyScrollView msv,MainActivity act,LayoutInflater li){
		super();
		myScrollView = msv;
		activity = act;
		inflater = li;
		mode = 0;
	}
	
	public NetworkAsyncTask(DownloadService srvc){
		service = srvc;
		mode = 1;
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
			while ((line = bReader.readLine()) != null) {
				if(line.indexOf("<item>") == -1)
					continue;
				String titleString,contentString,urlString,itemString = null;
				while(line.indexOf("</item>") == -1){
					itemString += line;
					line = bReader.readLine();
				}
				titleString = itemString.substring(itemString.indexOf("<title>")+"<title>".length(),itemString.indexOf("</title>"));
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
		}else if(mode == 1){
			MyDatabase myDatabase = service.getMyDatabase();
			myDatabase.refreashOffline(results);
			Toast.makeText(service, "Download Completed.", Toast.LENGTH_LONG).show();
			service.stopService(new Intent());
		}
	}

}
