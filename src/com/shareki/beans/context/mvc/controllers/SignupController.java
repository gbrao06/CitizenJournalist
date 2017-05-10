package com.shareki.beans.context.mvc.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.shareki.beans.context.mvc.models.LoginData;
import com.shareki.beans.context.mvc.models.SharekiDataContainer;
import com.shareki.beans.context.mvc.models.SharekiDataSelector;
import com.shareki.beans.context.mvc.models.SignupData;
import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekiuser;
 
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/signup")
public class SignupController extends SimpleFormController{
 
	@RequestMapping(method = RequestMethod.GET)
	public String getSignupForm(Model model) {
 
		System.out.println("getSignupForm");
		model.addAttribute("signupData",new SignupData());
	    
		return "signup";
 
	}

    @RequestMapping(headers ={"Accept=application/json"},method = RequestMethod.POST)
    public String postSignupForm(@ModelAttribute("signupData") SignupData signupData, BindingResult result,
    		HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
    
		System.out.println("postSignupForm");
		
		int ret=0;//signupData.addSignupuser();
		
		//Country country=SharekiDataContainer.getDataContainer().getCountryByName(signupData.getCitizenshipCountry());
		//Sharekiuser user=null;
		try
		{
			ret= signupData.addSignupuser();
		//else
		//	System.out.println("country==null");
		}
		catch(Exception ee)
		{
			System.out.println("postSignupForm::error;"+ee.getMessage()+":"+ee.getLocalizedMessage());
		}
		
		System.out.println("Ret value="+ret);
		if(ret==1 || ret==2)
		{
			return "redirect:/login";
		}
		else if(ret==0)
		{
			System.out.println("Invalid:SignupForm:");
			
			//result.addError("Invalid Data submitted: Kindly fill email,passwd,firstname,lastname,citizenship");
		}
		else if(ret==2)
		{
			System.out.println("User/Email Alredy exists:");
			
			//result.addError("User Already Exists");
		}
			
		if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                    System.err.println("Error: " + error.getCode() + " - "
                                    + error.getDefaultMessage());
            }
         
    }

		return "redirect:/signup";	
	}
	
}