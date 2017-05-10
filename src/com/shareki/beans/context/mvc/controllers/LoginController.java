package com.shareki.beans.context.mvc.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.shareki.beans.context.mvc.models.SharekiDataSelector;
import com.shareki.beans.context.mvc.rest.client.RestClientUtil;
 
@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/login")
public class LoginController extends SimpleFormController{
 
	@Autowired 
	RestClientUtil restClientUtil;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getLoginForm(Model model) {
 

		System.out.println("LoginForm:getLoginForm");
		model.addAttribute("loginData",new LoginData());
	    
		return "login";
 
	}
  
	@RequestMapping(headers ={"Accept=application/json"},produces={"application/json"},method = RequestMethod.POST)
    public String postLoginForm(@ModelAttribute("loginData") LoginData loginData, BindingResult result,
                    HttpSession session) {
	
		System.out.println("postLoginForm");
		if(restClientUtil.loginUser(loginData.getUsername(),loginData.getPasswd()))
		{
			System.out.println("RestClientUtil.loginUser: user successfully logged in");
			session.setAttribute("userId",loginData.getUsername());
			session.setAttribute("passwd",loginData.getPasswd());
			System.out.println("MATCH");
				
			return "redirect:/shareki";
		}
	
		
		if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                    System.err.println("Error: " + error.getCode() + " - "
                                    + error.getDefaultMessage());
            }
		}
        
		System.out.println("NOT MATCHED");
		
		return "login";
	}
	 
}