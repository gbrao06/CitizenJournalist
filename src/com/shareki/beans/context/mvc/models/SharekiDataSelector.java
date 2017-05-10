package com.shareki.beans.context.mvc.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.State;

//Create a MultipartFile variable to store the uploaded file. Alternatively, you can use the byte[] to store it, but i more prefer to use the MultipartFile, because it can get the uploaded file detail (file name, file size …) easily.

public class SharekiDataSelector {

	RestClientUtil restClientUtil;
	
	private  static SharekiDataSelector dataSelector=new SharekiDataSelector();
	
	public static SharekiDataSelector getDataSelector()
	{
		if(dataSelector==null)
			return (dataSelector=new SharekiDataSelector());
		
		return dataSelector;
	}
	
	private SharekiDataSelector()
	{
		System.out.println("SharekiDataSelector Constructor called");
		
		restClientUtil=new RestClientUtil();
		init();
	}

	private String fileName;
    private MultipartFile fileData;
    
	public String strSelectedCountry="india";
	public String strSelectedState;
	public String strSelectedCity;
	public String strSelectedVillage;
	public String dataDescription;
	public String title;

	
	public List<Country> countryList=new ArrayList<Country>();
	public List<State> stateList=new ArrayList<State>();	
	public List<City> cityList=new ArrayList<City>();
	
	public void init()
	{
		System.out.println("SharekiDataSelector init called");
		
		countryList.clear();
		stateList.clear();
		cityList.clear();
		countryList=SharekiDataContainer.getDataContainer().getCountryList();
		stateList=SharekiDataContainer.getDataContainer().getStateList();
		cityList=SharekiDataContainer.getDataContainer().getCityList();
		
	}

	public String getDataDescription() {
		return dataDescription;
	}

	public void setDataDescription(String dataDescription) {
		this.dataDescription = dataDescription;
	}

	public String getStrSelectedVillage() {
		return strSelectedVillage;
	}

	public void setStrSelectedVillage(String strSelectedVillage) {
		this.strSelectedVillage = strSelectedVillage;
	}

	public String getStrSelectedCountry() {
		return strSelectedCountry;
	}

	public void setStrSelectedCountry(String strSelectedCountry) {
		this.strSelectedCountry = strSelectedCountry;
		System.out.println("called:setSelectedCountry");

	}

	public String getStrSelectedState() {
		return strSelectedState;
	}

	public String getStrSelectedCity() {
		return strSelectedCity;
	}

	
	public void setStrSelectedState(String strSelectedState) {
		this.strSelectedState = strSelectedState;
	}

	public void setStrSelectedCity(String strSelectedCity) {
		this.strSelectedCity = strSelectedCity;
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
		this.countryList.clear();
		this.countryList = countryList;
	}

	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}

	public void setCityList(List<City> cityList) {
		
		this.cityList.clear();
		this.cityList = cityList;
	}
	
    public String getFileName() {
		return fileName;
	}

	public MultipartFile getFileData() {
		return fileData;
	}

	public void setFileName(String fileName) {
		System.out.println("UploadItem::setFileName called");
    	
		this.fileName = fileName;
	}

	public void setFileData(MultipartFile fileData) {
		System.out.println("UploadItem::setFileData-CommonsMultiPart called");
    	
		this.fileData = fileData;
	}

	public String getFilename() {
            return fileName;
    }
	
	public boolean addSharekinews(String userId,String passwd,String ip,String port,String context,String dir,String filename)
	{
		if(restClientUtil==null)
		{
			System.out.println("SharekiDataSelector:addSharekinews:RestClientUtil ==NULL");
			return false;
		}
		
		boolean va=restClientUtil.addSharekinews(userId,passwd, ip, port,context, dir, filename, dataDescription,strSelectedCountry,strSelectedCity,strSelectedVillage);
		
		return va;    	 
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
		
	
}

