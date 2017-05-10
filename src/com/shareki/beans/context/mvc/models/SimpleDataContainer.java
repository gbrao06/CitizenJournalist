package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.shareki.model.hybernate.entities.City;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.State;

//stores all static data at the initialization time
public class SimpleDataContainer {

	byte[] image;

	public String strSelectedCountry;
	public String strSelectedState;
	public String strSelectedCity;
	public String strSelectedVillage;
	public String dataDescription;

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public SimpleDataContainer(byte[] image,String story, String selectedCountry,String selectedCity,String selectedVillage){
		
		System.out.println("BlobDataContainer Constructor called");
		this.image=image;
		this.dataDescription=story;
		this.strSelectedCity=selectedCity;
		this.strSelectedCountry=selectedCountry;
		this.strSelectedVillage=selectedVillage;
	}
	
}
