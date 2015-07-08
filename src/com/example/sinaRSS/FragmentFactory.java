package com.example.sinaRSS;

import android.app.Fragment;

public class FragmentFactory {  
    public static Fragment getInstanceByIndex(int index) {  
        Fragment fragment = null;  
        switch (index) {  
            case 1:  
                fragment = new OnlineTabFragment();  //在线新闻页面
                break;   
            case 3:  
                fragment = new CollectionTabFragment();  //收藏新闻页面
                break;   
        }  
        return fragment;  //通过传入的index确定并返回指定页面
    }  
} 
