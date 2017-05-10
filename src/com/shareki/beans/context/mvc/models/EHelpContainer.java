package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Sharekinews;

//stores all static data at the initialization time
public class EHelpContainer {

	//private static EHelpContainer eHelpContainer=new EHelpContainer(null,null,null);
	
	RestClientUtil restClientUtil;
	public List<City> cityList=new ArrayList<City>();
	
	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}


	List<Sharekinews> allNews=new ArrayList<Sharekinews>();
	List<Sharekinews> cityNews=new ArrayList<Sharekinews>();
	List<Sharekinews> villageNews=new ArrayList<Sharekinews>();
	SharekiDataSelector dataSelector=SharekiDataSelector.getDataSelector();	
	SharekiDataContainer dataContainer=SharekiDataContainer.getDataContainer();	
	
	//String title;
	//String description;
	
	//String strCountrySelected;
	//String strCitySelected;
	//String strVillageSelected;
	
	/*
	public static EHelpContainer getEHelpContainer(String selectedVillage,String selectedCity,String selectedCountry){
		
		if(eHelpContainer==null)
		{
			return (eHelpContainer=new EHelpContainer(selectedVillage,selectedCity,selectedCountry));
		}
		else
		{
			eHelpContainer.strVillageSelected=selectedVillage;
			eHelpContainer.strCitySelected=selectedCity;
			eHelpContainer.strCountrySelected=selectedCountry;
		
			eHelpContainer.initAll();
			eHelpContainer.initCityEmergencies();
			eHelpContainer.initVillageEmergencies();
			
		}
		return eHelpContainer;
	}
	*/
	
	public SharekiDataSelector getDataSelector() {
		return dataSelector;
	}

	public SharekiDataContainer getDataContainer() {
		return dataContainer;
	}

	public void setDataSelector(SharekiDataSelector dataSelector) {
		this.dataSelector = dataSelector;
	}

	public void setDataContainer(SharekiDataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	public EHelpContainer(){
		
		System.out.println("EHepContainer Constructor called");
		
		//strVillageSelected=selectedVillage;
		//strCitySelected=selectedCity;
		//strCountrySelected=selectedCountry;
		restClientUtil=new RestClientUtil();
		//cityList.clear();
		//cityList=SharekiDataContainer.getDataContainer().getCityList();
		dataSelector=SharekiDataSelector.getDataSelector();
		dataContainer=SharekiDataContainer.getDataContainer();
		initAll();
		initCityEmergencies();
		initVillageEmergencies();
	}
	
	
	public static boolean isValidContent(String title,String description, String country)
	{
		if(country==null || country.equalsIgnoreCase("") || title==null || title.equalsIgnoreCase("") || description==null || description.equalsIgnoreCase("") )
			return false;
		return true;
	}


	private boolean isValidVillageContent(String title2, String description2,
			String strVillageSelected2, String strCitySelected2,
			String strCountrySelected2) {
		// TODO Auto-generated method stub
		if(strVillageSelected2==null || strVillageSelected2.equalsIgnoreCase("") || title2==null || title2.equalsIgnoreCase("") || description2==null || description2.equalsIgnoreCase("") 
				|| strCountrySelected2==null || strCountrySelected2.equalsIgnoreCase("") || strCitySelected2==null || strCitySelected2.equalsIgnoreCase(""))
			return false;
		
		return true;
	}

	public  void initAll()
	{
		System.out.println("MyStoryContainer init called");
		if(!isValidContent(dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedCountry()))
		{
			return;
		}

		allNews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

     	//country/{countryName}/city/{cityName}/news
 		//countries
     	//dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedCountry()))
 		String url = "http://localhost:8088/SharekiData/emergency/country/";
 		url+=dataSelector.getStrSelectedCountry();
     	url+="/";
     	url+="allnews";
     	
 		//news list
     	allNews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
        System.out.println("NewsListSize:"+allNews.size());

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

		cityNews.clear();
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
     	cityNews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
        System.out.println("NewsListSize:"+cityNews.size());

	}

	public void initVillageEmergencies()
	{
		System.out.println("MyStoryContainer init called");
		if(!isValidVillageContent(dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedVillage(),dataSelector.getStrSelectedCity(),dataSelector.getStrSelectedCountry()))
		{
			return;
		}

		villageNews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

     	//country/{countryName}/city/{cityName}/news
 		//countries
 		String url = "http://localhost:8088/SharekiData/emergency/country/";
 		url+=dataSelector.getStrSelectedCountry();
     	url+="/";
     	url+="city";
     	url+="/";
     	
     	url+=dataSelector.getStrSelectedCity();
     	url+="/";
     	
     	url+="village";
     	url+="/";
     	url+=dataSelector.getStrSelectedVillage();
     	url+="/";
     	url+="allnews";
     	
 		//news list
     	villageNews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
        System.out.println("NewsListSize:"+villageNews.size());

	}

	public List<Sharekinews> getAllNews() {
		return allNews;
	}

	public List<Sharekinews> getCityNews() {
		return cityNews;
	}

	public List<Sharekinews> getVillageNews() {
		return villageNews;
	}

/*	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getStrCountrySelected() {
		return strCountrySelected;
	}

	public String getStrCitySelected() {
		return strCitySelected;
	}

	public String getStrVillageSelected() {
		return strVillageSelected;
	}
*/
	public void setAllNews(List<Sharekinews> allNews) {
		this.allNews = allNews;
	}

	public void setCityNews(List<Sharekinews> cityNews) {
		this.cityNews = cityNews;
	}

	public void setVillageNews(List<Sharekinews> villageNews) {
		this.villageNews = villageNews;
	}

	/*public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStrCountrySelected(String strCountrySelected) {
		this.strCountrySelected = strCountrySelected;
	}

	public void setStrCitySelected(String strCitySelected) {
		this.strCitySelected = strCitySelected;
	}

	public void setStrVillageSelected(String strVillageSelected) {
		this.strVillageSelected = strVillageSelected;
	}

*/
	public boolean addSharekiEHelp(String userId,String passwd)
	{
		System.out.println("SharekiEHepCntainer:Entered:addSharekiEHelp");
		
		if(restClientUtil==null)
		{
			System.out.println("SharekiEHepCntainer:addSharekiEHep:RestClientUtil ==NULL");
			return false;
		}
		
		boolean va=restClientUtil.addSharekiEHelp(userId,passwd,dataSelector.getTitle(),dataSelector.getDataDescription(),dataSelector.getStrSelectedCountry(),dataSelector.getStrSelectedCity(),dataSelector.getStrSelectedVillage());
		
		return va;    	 
	}
	
}
