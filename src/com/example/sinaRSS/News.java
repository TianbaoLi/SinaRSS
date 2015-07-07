package com.example.sinaRSS;

public class News {
	private String title,content,uri;
	public News(String t,String c,String u){
		title = t;//标题
		content = c;//内容
		uri = u;//网址
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getContent(){
		return content;
	}
	
	public String getUri(){
		return uri;
	}

}
