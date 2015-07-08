package com.example.sinaRSS;

import com.example.sinaRSS.R;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
	private FragmentManager fragmentManager;  //页面
    private RadioGroup radioGroup; //页面按钮
    private MyDatabase myDatabase;//数据库

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_main);  
        
        myDatabase = new MyDatabase(openOrCreateDatabase(MyDatabase.FILE_NAME, Context.MODE_PRIVATE, null));
  
        fragmentManager = getFragmentManager();  
        radioGroup = (RadioGroup) findViewById(R.id.rgTab);  
        FragmentTransaction transaction = fragmentManager.beginTransaction();  
        Fragment fragment = FragmentFactory.getInstanceByIndex(1);  
        transaction.replace(R.id.content, fragment);  
        transaction.commit();  
        radioGroup.setOnCheckedChangeListener(
        		new RadioGroup.OnCheckedChangeListener() {
			@Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {   //通过按顶部按钮实现切换页面
				
				Log.v("demo", checkedId + " " + radioGroup.getCheckedRadioButtonId());

				RadioButton radioButton = (RadioButton)findViewById(checkedId);
				
				int checked_index = 0;//标记当前切换状态
				
				if(radioButton.getText().equals("在线")){
					checked_index = 1;
				}else if(radioButton.getText().equals("我的新闻")){
					checked_index = 3;
				}
//				
                FragmentTransaction transaction = fragmentManager.beginTransaction();  
                Fragment fragment = FragmentFactory.getInstanceByIndex(checked_index);  
                transaction.replace(R.id.content, fragment);  
                transaction.commit();  
            }  
        });  
        
        Button button;
        button=(Button) findViewById(R.id.changetype);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent();
				intent.setClass(MainActivity.this,ChangeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
    }  

    public MyDatabase getMyDatabase(){
    	return myDatabase;
    }
    
}
