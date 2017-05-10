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
public class CityStoryContainer {

	//private String strCountrySelected;
	//private String strCitySelected;
	
	List<Sharekinews> cityENews=new ArrayList<Sharekinews>();
	public List<Sharekinews> getCityENews() {
		return cityENews;
	}

	public SharekiDataSelector getDataSelector() {
		return dataSelector;
	}

	public void setCityENews(List<Sharekinews> cityENews) {
		this.cityENews = cityENews;
	}

	public void setDataSelector(SharekiDataSelector dataSelector) {
		this.dataSelector = dataSelector;
	}

	SharekiDataSelector dataSelector=SharekiDataSelector.getDataSelector();	
	
	List<Sharekinews> listNews=new ArrayList<Sharekinews>();
    	
	public CityStoryContainer(){
		
		System.out.println("CityStoryContainer Constructor called");
		//this.strCountrySelected=dataSelector.getStrSelectedCountry();
		//this.strCitySelected=dataSelector.getStrSelectedCity();
		
		init();
	}
	
	public static boolean isValidContent(String strSelectedCountry,String strSelectedCity)
	{
		if(strSelectedCountry==null || strSelectedCountry.equalsIgnoreCase("") || strSelectedCity==null || strSelectedCity.equalsIgnoreCase("") )
			return false;
		return true;
	}
		
	public void init()
	{
		System.out.println("CityStoryContainer init called");
		if(!isValidContent(dataSelector.getStrSelectedCountry(),dataSelector.getStrSelectedCity()))
		{
			return;
		}
		listNews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

     	//country/{countryName}/city/{cityName}/news
 		//countries
 		String url = "http://localhost:8088/SharekiData/country/";
 		url+=dataSelector.getStrSelectedCountry();
     	url+="/";
     	url+="city";
     	url+="/";
     	url+=dataSelector.getStrSelectedCity();
     	url+="/";
     	url+="news";
     	
     	
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

	/*
	public String getStrCountrySelected() {
		return strCountrySelected;
	}


	public String getStrCitySelected() {
		return strCitySelected;
	}


	public void setStrCountrySelected(String strCountrySelected) {
		this.strCountrySelected = strCountrySelected;
	}


	public void setStrCitySelected(String strCitySelected) {
		this.strCitySelected = strCitySelected;
	}
*/
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
	
	public static boolean isValidCityContent(String title,String description,String city, String country)
	{
		if(country==null || country.equalsIgnoreCase("") || city==null || city.equalsIgnoreCase("") || title==null || title.equalsIgnoreCase("") || description==null || description.equalsIgnoreCase("") )
			return false;
		return true;
	}

	public void initCityEmergencies()
	{
		System.out.println("MyStoryContainer init called");
		if(!isValidCityContent(dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedCity(),dataSelector.getStrSelectedCountry()))
		{
			return;
		}

		cityENews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

     	//country/{countryName}/city/{cityName}/news
 		//countries
     	//dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedCity(),dataSelector.getStrSelectedCountry()))
 		String url = "http://localhost:8088/SharekiData/emergency/country/";
 		url+=dataSelector.getStrSelectedCountry();
     	url+="/";
     	url+="city";
     	url+="/";
     	url+=dataSelector.getStrSelectedCity();
     	url+="/";
     	url+="allnews";
     	
 		//news list
     	cityENews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
        System.out.println("NewsListSize:"+cityENews.size());

	}


}
