package com.example.sinaRSS;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	private NetworkAsyncTask networkAsyncTask;
	private MyDatabase myDatabase;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate(){
		myDatabase = new MyDatabase(openOrCreateDatabase(MyDatabase.FILE_NAME, Context.MODE_PRIVATE, null));
	}
	
	@Override
	public void onStart(Intent intent, int startId){
		networkAsyncTask = new NetworkAsyncTask(this);
		networkAsyncTask.execute(500);
	}
	
	@Override
	public void onDestroy(){
		
	}
	
	public MyDatabase getMyDatabase(){
		return myDatabase;
	}
}
