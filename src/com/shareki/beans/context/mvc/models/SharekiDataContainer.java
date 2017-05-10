package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekiuser;
import com.shareki.model.hybernate.entities.State;

//stores all static data at the initialization time
public class SharekiDataContainer {

	private static SharekiDataContainer dataContainer=new SharekiDataContainer();
	
	List<Country> countryList=new ArrayList<Country>();
	List<State> stateList=new ArrayList<State>();
	
	List<City> cityList=new ArrayList<City>();

	List<Sharekiuser> userList=new ArrayList<Sharekiuser>();

	private SharekiDataContainer(){
		
		System.out.println("SharekiDataContainer Constructor called");
		
		init();
	}
	
	public static SharekiDataContainer getDataContainer()
	{
		if(dataContainer==null)
			return ((dataContainer=new SharekiDataContainer()));
		return dataContainer;
	}
	
	private void init()
	{
		System.out.println("SharekiDataContainer init called");
		
		countryList.clear();
		stateList.clear();
		cityList.clear();
		userList.clear();
		
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

 		//countries
 		String url = "http://localhost:8088/SharekiData/countries";
        
 		String urlStates = "http://localhost:8088/SharekiData/country/india/states";

 		String urlCities = "http://localhost:8088/SharekiData/country/india/cities";

 		//country list
 		List<Country> countryList=new ArrayList<Country>();
        countryList=(Arrays.asList(restTemplate.getForObject(url, Country[].class)));
 		setCountryList(countryList);
 		
 		//cityList
 		List<City> cityList=new ArrayList<City>();
 		cityList=(Arrays.asList(restTemplate.getForObject(urlCities, City[].class)));
 		setCityList(cityList);
 		
 		//stateList
 		List<State> stateList=new ArrayList<State>();
 		stateList=(Arrays.asList(restTemplate.getForObject(urlStates, State[].class)));
 		setStateList(stateList);
 		
 		//UserList
 		String urlUsers = "http://localhost:8088/SharekiData/users";
 		userList=(Arrays.asList(restTemplate.getForObject(urlUsers, Sharekiuser[].class)));
 		setUserList(userList);
 		
 		
 		System.out.println("UserListSize1:"+userList.size());
 		System.out.println("CountryListSize1:"+countryList.size());

	}
	public List<Country> getCountryList() {
		return countryList;
	}

	public List<State> getStateList() {
		return stateList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}

	public void setUserList(List<Sharekiuser> userList) {
		this.userList = userList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
		
	public  Country getCountryByName(String countryName)
	{
		if(countryName==null || countryName.equalsIgnoreCase(""))
			return null;
		
		for(int i=0;i<countryList.size();i++)
		{
			if(countryList.get(i).getName().equalsIgnoreCase(countryName))
				return countryList.get(i);
		}
		
		return null;
	}

	public  Sharekiuser getUserByUserId(String userId)
	{
		if(userId==null || userId.equalsIgnoreCase(""))
			return null;
		
		for(int i=0;i<userList.size();i++)
		{
			if(userList.get(i).getUserid().equalsIgnoreCase(userId))
				return userList.get(i);
		}
		
		return null;
		
	}

	
	/*
	public  int addSignupuser(String userId,String passwd,String email,String countryName,String firstname,String lastname,String phone)
	{
		
		 Country country=SharekiDataContainer.getDataContainer().getCountryByName(countryName);
		try
		{
		if(country!=null)
			return RestClientUtil.addSignupuser(email, password, email, country.getCode(), firstname, lastname, null);
		else
			System.out.println("country==null");
		}
		catch(Exception ee)
		{
			System.out.println("error;"+ee.getMessage());
		}
		return 0;
		
		 
	}
	*/
	
}
