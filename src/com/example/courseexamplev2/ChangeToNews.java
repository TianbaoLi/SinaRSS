package com.example.courseexamplev2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ChangeToNews extends Activity {

	//private LayoutInflater inflater = null;
	private Button btn1;
	private Button btn2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_change);
		//View tempView = inflater.inflate(R.layout.activity_change, null);
		btn1=(Button)findViewById(R.id.changebtn1);//YES
		btn2=(Button)findViewById(R.id.changebtn2);//No
		btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ChangeToNews.this, MainActivity.class);
				startActivity(intent);
				ChangeToNews.this.finish();
				
			}
		});
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ChangeToNews.this, MainActivity.class);
				startActivity(intent);
				ChangeToNews.this.finish();
				
			}
		});
	}
}
