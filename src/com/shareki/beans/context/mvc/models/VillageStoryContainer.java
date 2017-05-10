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
public class VillageStoryContainer {

	//private String strCountrySelected;
	//private String strCitySelected;
	//private String strVillageSelected;
	
	List<Sharekinews> villageNews=new ArrayList<Sharekinews>();

	List<Sharekinews> listNews=new ArrayList<Sharekinews>();
    	 
	SharekiDataSelector dataSelector=SharekiDataSelector.getDataSelector();
	
	public List<Sharekinews> getVillageNews() {
		return villageNews;
	}

	public SharekiDataSelector getDataSelector() {
		return dataSelector;
	}

	public void setVillageNews(List<Sharekinews> villageNews) {
		this.villageNews = villageNews;
	}

	public void setDataSelector(SharekiDataSelector dataSelector) {
		this.dataSelector = dataSelector;
	}

	public VillageStoryContainer(){
		
		System.out.println("VillageStoryContainer Constructor called");
		//this.strCountrySelected=strCountrySelected;
		//this.strCitySelected=strCitySelected;
		//this.strVillageSelected=strVillageSelected;
		
		init();
	}
	
	public static boolean isValidContent(String strSelectedCountry,String strSelectedCity,String strSelectedVillage)
	{
		if(strSelectedCountry==null || strSelectedCountry.equalsIgnoreCase("") || strSelectedCity==null || strSelectedCity.equalsIgnoreCase("") || strSelectedVillage==null || strSelectedVillage.equalsIgnoreCase("") )
			return false;
		return true;
	}
		
	public void init()
	{
		System.out.println("VillageStoryContainer init called");
		if(!isValidContent(dataSelector.getStrSelectedCountry(),dataSelector.getStrSelectedCity(),dataSelector.getStrSelectedVillage()))
		{
			System.out.println("VillageStoryContainerInvalid Data:countrySelected"+dataSelector.getStrSelectedCountry()+":citySelected:"+dataSelector.getStrSelectedCity()+":villageSelected"+dataSelector.getStrSelectedVillage());
			
			return;
		}
		
		listNews.clear();
		RestTemplate restTemplate = new RestTemplate();
     	restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

 		//countries
     	//country/{countryName}/city/{cityName}/village/{villageName}/news
 		String url = "http://localhost:8088/SharekiData/country/";
        
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
     	url+="news";
 		//country list
 		listNews=(Arrays.asList(restTemplate.getForObject(url, Sharekinews[].class)));
 		
 		System.out.println("NewsListSize:"+listNews.size());
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

	
/*
 *
 * <div class="flexslider">
    <ul class="slides">
        <li>
            <a href="url"><img src="image" alt="alt"></a>
        </li>
        <li>
            <a href="url"><img src="image" alt="alt"></a>
        </li>
        etc...
    </ul>
</div>
 * */
 
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


	public String getStrVillageSelected() {
		return strVillageSelected;
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
	
	//img  src="data:image/png;base64,PASTE THE BYTE ARRAY HERE WHICH IS OBTAINED FROM JAVA PROGRAM" alt="IMG DESC">
/*	<div id="upper3" class="curved container">
    <div id="congrats3" class="curved container">
       		<c:forEach items="${villageStoryContainer.listNews}" var="item">
	  
   <div class="col20">
	    	<img src="data:image/png;base64,${villageStoryContainer.imagetBase64(item)}" alt="xrv"/>
	    	<output style="align:left;">${item.news}</output>
	    </div>

	</c:forEach>

</div>
*/
	
	//Read more: http://mrbool.com/how-to-convert-image-to-byte-array-and-byte-array-to-image-in-java/25136#ixzz2m7DBXVi6
}
