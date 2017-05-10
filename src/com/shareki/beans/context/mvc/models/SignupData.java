package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.Sharekiuser;

public class SignupData {

	@Autowired
	RestClientUtil restClientUtil;
	
	public SignupData() {
		// TODO Auto-generated constructor stub
		countryList.clear();
		countryList=SharekiDataContainer.getDataContainer().getCountryList();
		restClientUtil=new RestClientUtil();
	}

//	String userId;
	String password;
	
	String email;
	String firstname;
	String lastname;
	
	
	List<Country> countryList=new ArrayList<Country>();
	
	public List<Country> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<Country> countryList) {
		this.countryList = countryList;
	}

	String citizenshipCountry;
	
	
	
	public String getCitizenshipCountry() {
		return citizenshipCountry;
	}

	public void setCitizenshipCountry(String citizenshipCountry) {
		this.citizenshipCountry = citizenshipCountry;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCitizenshipCountryPK() {
		Country c=restClientUtil.getCountryByName(citizenshipCountry);
		if(c!=null)
			return c.getCode();
		
		return null;
	}
	
	public int addSignupuser()
	{
		//check if user exist
		Sharekiuser user=null;
		user=SharekiDataContainer.getDataContainer().getUserByUserId(email);
		
		if(user!=null)//existing user
		{
			return 2;
		}
		
		//new user insert
		Country country=SharekiDataContainer.getDataContainer().getCountryByName(citizenshipCountry);
		try
		{
		if(country!=null)
		{
			if(restClientUtil==null)
			{
				System.out.println("SignupData:addSignupuser: REST CLIENT UTIL ITSELF =NULL");
				return 0;
			}
			return restClientUtil.addSignupuser(email, password, email, country.getCode(), firstname, lastname, null);
		}
		else
			System.out.println("country==null");
		}
		catch(Exception ee)
		{
			System.out.println("SignupData:addSignupuser:error;"+ee.getMessage());
		}
		
		return 0;
	}
}
