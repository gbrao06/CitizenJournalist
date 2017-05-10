package com.shareki.annotation.springconfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.shareki.beans.context.mvc.models.CityStoryContainer;
import com.shareki.beans.context.mvc.models.EHelpContainer;
import com.shareki.beans.context.mvc.models.FirstClass;
import com.shareki.beans.context.mvc.models.LoginData;
import com.shareki.beans.context.mvc.models.SharekiDataContainer;
import com.shareki.beans.context.mvc.models.SharekiDataSelector;
import com.shareki.beans.context.mvc.models.SignupData;
import com.shareki.beans.context.mvc.models.UploadFileValidator;
import com.shareki.beans.context.mvc.models.FileUploadItem;
import com.shareki.beans.context.mvc.models.VillageStoryContainer;
import com.shareki.beans.context.mvc.rest.client.RestClientUtil;

 
//1. This is replacement for web.xml where servletname is defined and the sping look at name-servlet.xml for beans.
//2. Don't forget to override getServletMappings with "/", else URL requests will not be redirected to Spring Dispatcher 
    @Configuration
    @ComponentScan(basePackages={"com.shareki.beans.context.mvc.controllers", "com.shareki.beans.context.mvc.models","com.shareki.beans.context.mvc.rest.client"})
    public class RootConfig {

    	@Bean
    	public FirstClass firstClass() { 
    	      return new FirstClass();
    	}
    
    	@Bean
    	public FileUploadItem uploadItem() { 
    	      return new FileUploadItem();
    	}
    	
    	@Bean
    	public UploadFileValidator fileValidator()
    	{
    		return new UploadFileValidator();
    	}
    	
    	@Bean
    	public SharekiDataContainer dataContainer() { 
    	      return  SharekiDataContainer.getDataContainer();
    	}
    	
    	@Bean
    	public SharekiDataSelector dataSelector() { 
    	      return  SharekiDataSelector.getDataSelector();
    	}
    
    	@Bean
    	public LoginData loginData() { 
    	      return  new LoginData();
    	}
    
    	@Bean
    	public CityStoryContainer cityContainer() { 
    	      return  new CityStoryContainer();
    	}
    
    	@Bean
    	public EHelpContainer ehelpContainer() { 
    	      return  new EHelpContainer();
    	}
    
    	@Bean
    	public VillageStoryContainer villageContainer() { 
    	      return  new VillageStoryContainer();
    	}
    
    	@Bean
    	public SignupData signupData() { 
    	      return  new SignupData();
    	}
    	
    	@Bean
    	public RestClientUtil restClientUtil()
		{
			return new RestClientUtil();
		}
		/*
		@Bean
    	public RestTemplate restTemplate() { 
    	      RestTemplate restTemplate=  new RestTemplate();
    	      
    	      restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
    	      restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    	      
    	      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    	       
    	     return restTemplate;
    	}
    	*/
    }

