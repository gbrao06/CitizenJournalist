package com.shareki.annotation.springconfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.springframework.cglib.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
//1. This is replacement for web.xml where servletname is defined and the sping look at name-servlet.xml for beans.
//2. Don't forget to override getServletMappings with "/", else URL requests will not be redirected to Spring Dispatcher 
    @Configuration
    @EnableWebMvc
    
    @ComponentScan(basePackages={"com.shareki.beans.context.mvc.controllers","com.shareki.beans.context.mvc.rest.client"})
    public class WebMvcConfig extends WebMvcConfigurationSupport {

    	@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    		System.out.println("Inside WebMVCConfig::configureMessageConverters");
    	
    		converters.add(jsonJacksonConvertor());
            addDefaultHttpMessageConverters(converters);
        }
    	

    	@Bean
    	public MappingJacksonHttpMessageConverter jsonJacksonConvertor()
    	{
    		System.out.println("Inside WebAppConfig::jsonJackson1");
        	
    		MappingJacksonHttpMessageConverter convertor=new MappingJacksonHttpMessageConverter();
			//convertor.setSupportedMediaTypes(MediaType.APPLICATION_JSON);
    		
    		return convertor;
    		
    	}
    
    	@Bean
        public InternalResourceViewResolver viewResolver() {
            
    		System.out.println("Inside WebAppConfig::viewResolver");
    		InternalResourceViewResolver resolver = 
                        new InternalResourceViewResolver();
            //resolver.setPrefix("/WEB-INF/");
    		resolver.setPrefix("/");//keep the jsps outside WEB-INF
            resolver.setSuffix(".jsp");
           
            return resolver;
        }
    	
    	
    	@Bean
        public CommonsMultipartResolver multipartResolver() {
            
    		System.out.println("Inside WebAppConfig::viewResolver");
    		CommonsMultipartResolver resolver = 
                        new CommonsMultipartResolver();
            //resolver.setPrefix("/WEB-INF/");
    		
            resolver.setMaxUploadSize(10000000);
            return resolver;
        }
    	
    	@Bean
        public HandlerMapping resourceHandlerMapping() {
    		System.out.println("Inside WebAppConfig::resourceHandlerMapping");
    		AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
            handlerMapping.setOrder(-1);
            return handlerMapping;
        }
    	
    	//Stores registrations of resource handlers for serving static resources such as images, css files and others through Spring MVC including setting cache headers optimized for efficient loading in a web browser. Resources can be served out of locations under web application root, from the classpath, and others.
    	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
    		System.out.println("Inside WebAppConfig::addResourceHandler");
    	    registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    	}
    	    	
    	//Working with Configurer Adapter equalent to <mvc:resources mapping="/resources/**" location="/resources/" /> in web.xml
   /* 	@Override
    	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	    registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    	}
   */ 	
/*
    	@Bean
    	public RestTemplate restTemplate() {
    	    RestTemplate restTemplate = new RestTemplate();
    	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();

    	    converters.add(jsonJacksonConvertor());
    	    restTemplate.setMessageConverters(converters);

    	    return restTemplate;
    	}
    	
    	@Bean
    	public MappingJacksonHttpMessageConverter jsonJacksonConvertor()
    	{
    		System.out.println("Inside WebAppConfig::jsonJackson1");
        	
    		MappingJacksonHttpMessageConverter convertor=new MappingJacksonHttpMessageConverter();
			//convertor.setSupportedMediaTypes(MediaType.APPLICATION_JSON);
    		
    		return convertor;
    		
    	}
  */  	
    	/*
    	@Bean
    	    public UrlBasedViewResolver setupViewResolver() {
    	
    	        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
    	
    	        resolver.setPrefix("/WEB-INF/");
    	
    	        resolver.setSuffix(".jsp");
    	
    	        resolver.setViewClass(JstlView.class);
    	
    	        return resolver;
    	
    	    }
    	*/
    	 

    }

