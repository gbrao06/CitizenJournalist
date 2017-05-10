package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.catalina.util.Base64;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.shareki.model.hybernate.entities.Sharekinews;

//stores all static data at the initialization time
public class MyStoryContainer {

	private String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	private String password;
	
	
	List<Sharekinews> listNews=new ArrayList<Sharekinews>();
    	
	public MyStoryContainer(String userId,String password){
		
		System.out.println("MyStoryContainer Constructor called");
		this.userId=userId;
		this.password=password;
		
		init();
	}
	
	public static boolean isValidContent(String userId,String password)
	{
		if(userId==null || userId.equalsIgnoreCase("") || password==null || password.equalsIgnoreCase("") )
			return false;
		return true;
	}
		
	public void init()
	{
		System.out.println("MyStoryContainer init called");
		if(!isValidContent(userId,password))
		{
			return;
		}
		listNews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

     	//country/{countryName}/city/{cityName}/news
 		//countries
 		String url = "http://localhost:8088/SharekiData/user/";
 		url+=userId;
     	url+="/";
     	url+="allnews";
     	
     	
 		//news list
 		listNews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
        System.out.println("NewsListSize:"+listNews.size());

	}
	
	public List<Sharekinews> getListNews() {
		return listNews;
	}


	public void setListNews(List<Sharekinews> listNews) {
		this.listNews = listNews;
	}



	public String imagetBase64(Sharekinews item)
	{
		if(item==null || item.getImage()==null)
			return null;
		try
		{
			String base64Str=Base64.encode(item.getImage());
			System.out.println("Base64 Image String:"+base64Str);
			
			return base64Str;
		}
		catch(Exception ee)
		{
			System.out.println("Exception:Base64 Image String:");
			
			return null;
		}
	}
	

}
