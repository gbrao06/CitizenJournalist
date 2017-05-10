package com.shareki.beans.context.mvc.controllers;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.shareki.beans.context.mvc.models.CityStoryContainer;
import com.shareki.beans.context.mvc.models.FileUploadItem;
import com.shareki.beans.context.mvc.models.LoginData;
import com.shareki.beans.context.mvc.models.SharekiDataSelector;
import com.shareki.model.hybernate.entities.Country;

import org.springframework.beans.factory.config.Scope;
//This is Spring MVC model

@SuppressWarnings("deprecation")
@Controller

public class CityEHelpsController extends SimpleFormController{

	//Either we can return a String or ModelView object which will have an attribute with URL
	//String is name of the page
		
	@RequestMapping(value="/cityehelps",method = RequestMethod.GET)
    public  String getEHelpCityStoriesForm(Model model, HttpServletRequest request, 
   	        HttpServletResponse response) {
    	
		System.out.println("Get:getCityStoriesForm");
		
		String selectedCity=(String) request.getSession().getAttribute("selectedCity");
   	
		String selectedCountry=(String) request.getSession().getAttribute("selectedCountry");
		CityStoryContainer city=new CityStoryContainer();
		city.initCityEmergencies();
		model.addAttribute("cityStoryContainer",city);
   
   	//create the new object
       return "cityehelps";
    }
	
	@RequestMapping(value="/cityehelps",method = RequestMethod.POST)
    public String postEHelpCityStoriesForm(@ModelAttribute("cityStoryContainer") CityStoryContainer cityStoryContainer, BindingResult result,
    		 HttpServletRequest request, HttpServletResponse response,
                    HttpSession session) {
    	//model.addAttribute("newItem", uploadItem);    
    	System.out.println("POST:postCityStoriesForm"); 
        
    	System.out.println("POST:Selected City:"+cityStoryContainer.getDataSelector().getStrSelectedCity()); 
    	System.out.println("POST:Selected Country:"+cityStoryContainer.getDataSelector().getStrSelectedCountry()); 
    	System.out.println("POST:City News Cize:"+cityStoryContainer.getListNews().size()); 
    	
    	
    	if (result.hasErrors()) {
                    for (ObjectError error : result.getAllErrors()) {
                            System.err.println("Error: " + error.getCode() + " - "
                                            + error.getDefaultMessage());
                    }
                    return "/cityehelps";
            }
        
	       session.setAttribute("selectedCountry", cityStoryContainer.getDataSelector().getStrSelectedCountry());
           session.setAttribute("selectedCity",cityStoryContainer.getDataSelector().getStrSelectedCity());
      
            //if user has any comments to do etc,,reditrect to comments pa 		 
            
            return "redirect:/cityehelps";
    }
    	 
}
