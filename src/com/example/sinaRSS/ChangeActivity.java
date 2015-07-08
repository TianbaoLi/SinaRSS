package com.example.sinaRSS;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change);
		
		RadioGroup      m_RadioGroup;  
        final RadioButton     m_Radio0, m_Radio1, m_Radio2, m_Radio3, m_Radio4, m_Radio5, m_Radio6;
        
        m_RadioGroup = (RadioGroup) findViewById(R.id.typegroup);  
        m_Radio0 = (RadioButton) findViewById(R.id.radioButton0);  
        m_Radio1 = (RadioButton) findViewById(R.id.radioButton1);  
        m_Radio2 = (RadioButton) findViewById(R.id.radioButton2);  
        m_Radio3 = (RadioButton) findViewById(R.id.radioButton3); 
        m_Radio4 = (RadioButton) findViewById(R.id.radioButton4); 
        m_Radio5 = (RadioButton) findViewById(R.id.radioButton5); 
        m_Radio6 = (RadioButton) findViewById(R.id.radioButton6); 
        
        m_RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId)  
            {  
                // TODO Auto-generated method stub  
            	int index=0;
            	if(checkedId==m_Radio0.getId())
            		index=0;
            	else if(checkedId==m_Radio1.getId())
            		index=1;
            	else if(checkedId==m_Radio2.getId())
            		index=2;
            	else if(checkedId==m_Radio3.getId())
            		index=3;
            	else if(checkedId==m_Radio4.getId())
            		index=4;
            	else if(checkedId==m_Radio5.getId())
            		index=5;
            	else if(checkedId==m_Radio6.getId())
            		index=6;
            	NetworkAsyncTask.setUrl(index);
                
            	//finish();
            	Intent intent = new Intent();
				intent.setClass(ChangeActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
            	
            }  
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change, menu);
		return true;
	}

}
