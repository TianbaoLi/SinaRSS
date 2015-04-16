package com.example.courseexamplev2;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {
	private FragmentManager fragmentManager;  
    private RadioGroup radioGroup; 
    private MyDatabase myDatabase;

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
            public void onCheckedChanged(RadioGroup group, int checkedId) {   
				
				Log.v("demo", checkedId + " " + radioGroup.getCheckedRadioButtonId());
//				if(radioGroup.getCheckedRadioButtonId() == ""){
//					
//				}
				RadioButton radioButton = (RadioButton)findViewById(checkedId);
				
				int checked_index = 0;
				
				if(radioButton.getText().equals("在线")){
					checked_index = 1;
				}else if(radioButton.getText().equals("离线")){
					checked_index = 2;
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
    }  

    public MyDatabase getMyDatabase(){
    	return myDatabase;
    }
    
}
