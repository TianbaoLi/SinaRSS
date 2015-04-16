package com.example.courseexamplev2;

public class News {
	private String title,content,uri;
	public News(String t,String c,String u){
		title = t;
		content = c;
		uri = u;
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
