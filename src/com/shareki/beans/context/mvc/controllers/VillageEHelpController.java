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
import com.shareki.beans.context.mvc.models.VillageStoryContainer;
import com.shareki.model.hybernate.entities.Country;

import org.springframework.beans.factory.config.Scope;
//This is Spring MVC model

@SuppressWarnings("deprecation")
@Controller

public class VillageEHelpController extends SimpleFormController{

	//Either we can return a String or ModelView object which will have an attribute with URL
	//String is name of the page
	
	//default call on load page 
	//Get is to populate data in the form
	@RequestMapping(value="/villageehelps",method = RequestMethod.GET)
    public  String getEHelpVillageStoriesForm(Model model, HttpServletRequest request, 
   	        HttpServletResponse response) {
    	
		System.out.println("Get:getCityStoriesForm");
		
		String selectedCity=(String) request.getSession().getAttribute("selectedCity");
   	
		String selectedCountry=(String) request.getSession().getAttribute("selectedCountry");
   	
		String selectedVillage=(String) request.getSession().getAttribute("selectedVillage");
		VillageStoryContainer vv=new VillageStoryContainer();
		vv.initVillageEmergencies();
		model.addAttribute("villageStoryContainer",vv);
   
   	//create the new object
       return "/villageehelps";
    }

    @RequestMapping(value="/villageehelps",method = RequestMethod.POST)
    public String postEHelpVillageStoriesForm(@ModelAttribute("villageStoryContainer") VillageStoryContainer villageStoryContainer, BindingResult result,
    		 HttpServletRequest request, HttpServletResponse response,
                    HttpSession session) {
    	//model.addAttribute("newItem", uploadItem);    
    	System.out.println("POST:postVillageStoriesForm"); 
        
    	System.out.println("POST:Selected City:"+villageStoryContainer.getDataSelector().getStrSelectedCity()); 
    	System.out.println("POST:Selected Country:"+villageStoryContainer.getDataSelector().getStrSelectedCountry()); 
    	System.out.println("POST:Selected Village:"+villageStoryContainer.getDataSelector().getStrSelectedVillage()); 
    	
    	System.out.println("POST:Village EHelp News Cize:"+villageStoryContainer.getListNews().size()); 
    	
    	
    	if (result.hasErrors()) {
                    for (ObjectError error : result.getAllErrors()) {
                            System.err.println("Error: " + error.getCode() + " - "
                                            + error.getDefaultMessage());
                    }
                    return "/villageehelps";
            }
        
	       session.setAttribute("selectedCountry", villageStoryContainer.getDataSelector().getStrSelectedCountry());
           session.setAttribute("selectedCity",villageStoryContainer.getDataSelector().getStrSelectedCity());
           session.setAttribute("selectedVillage",villageStoryContainer.getDataSelector().getStrSelectedVillage());
           
            //if user has any comments to do etc,,reditrect to comments pa 		 
            
            return "redirect:/villageehelps";
    }
 
}
