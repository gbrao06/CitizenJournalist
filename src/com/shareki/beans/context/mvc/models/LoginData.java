package com.shareki.beans.context.mvc.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.Sharekiuser;

public class LoginData {

	public LoginData() {
		// TODO Auto-generated constructor stub
	}

	String username;
	String passwd;
	public String getUsername() {
		return username;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
}
