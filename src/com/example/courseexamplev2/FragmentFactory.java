package com.example.courseexamplev2;

import android.app.Fragment;

public class FragmentFactory {  
    public static Fragment getInstanceByIndex(int index) {  
        Fragment fragment = null;  
        switch (index) {  
            case 1:  
                fragment = new OnlineTabFragment();  
                break;  
            case 2:  
                fragment = new OfflineTabFragment();  
                break;  
            case 3:  
                fragment = new CollectionTabFragment();  
                break;   
        }  
        return fragment;  
    }  
} 
