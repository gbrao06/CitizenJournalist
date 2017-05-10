package com.shareki.beans.context.mvc.rest.client;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.shareki.beans.context.mvc.models.SharekiDataSelector;
import com.shareki.model.hybernate.entities.Country;
import com.shareki.model.hybernate.entities.Sharekinews;
import com.shareki.model.hybernate.entities.Sharekiuser;

@Component(value = "userRestClient")
public class RestClientUtil {

	
	private RestTemplate restTemplate;
	
	public RestClientUtil() {
		// TODO Auto-generated constructor stub
		restTemplate=new RestTemplate();
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

	public  Sharekiuser getUserByUserId(String userId,String pass)
	{
		if(userId==null || userId.equalsIgnoreCase("") || pass==null || pass.equalsIgnoreCase(""))
			return null;
		
//		RestTemplate restTemplate = new RestTemplate();
//       restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
//        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        //user/{userId}/passwd/{passwd}
     	
        String url = "http://localhost:8088/SharekiData/user/";
 		url+=userId;
 		url+="/";
 		url+="passwd";
 		url+="/";
 		url+=pass;
     	//List<Sharekiuser> ulist=new ArrayList<Sharekiuser>();
 		System.out.println("RestClientUtil:Before:getUserByUserId");
		
 		//Object [] list=restTemplate.getForObject(url, Sharekiuser[].class);
 		
 		ResponseEntity<Sharekiuser> entity=restTemplate.getForEntity(url, Sharekiuser.class);
 		Sharekiuser user=entity.getBody();
 		
 		if(user!=null && entity!=null)
 		{
 			System.out.println("RestClientUtil:getUserByUserId:Returning valid User="+user.getUserid());
 			
 			return user;
 		}
 		
 		System.out.println("RestClientUtil:getUserByUserId:Either ResponseEntity Or User=null");
		
     	//if(list!=null && list.length>0)
     	//	return (Sharekiuser)list[0];
     	
 		return null;
	}
	public  Country getCountryByName(String countryName)
	{
		if(countryName==null || countryName.equalsIgnoreCase(""))
			return null;
			
	//	RestTemplate restTemplate = new RestTemplate();
    //    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    //    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        //user/{userId}/passwd/{passwd}
     	
        String url = "http://localhost:8088/SharekiData/country/";
 		
        url+=countryName;
        
     	List<Country> ulist=new ArrayList<Country>();
     	ulist.clear();
 		//ulist=(Arrays.asList(restTemplate.getForObject(url, Country[].class)));
     	ResponseEntity<Country> entity=restTemplate.getForEntity(url, Country.class);
     	Country country = entity.getBody();
     	
     	
 		//if(ulist.size()>0)
 		//	return ulist.get(0);
 		
 		return country;
 		
	}
	
	public boolean loginUser(String userId,String passwd)
	{
		if(userId==null || userId.equalsIgnoreCase("") || passwd==null || passwd.equalsIgnoreCase(""))
			return false;
		
		try{
		//RestTemplate restTemplate = new RestTemplate();
        //restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        //user/{userId}/passwd/{passwd}
     	
        String url = "http://localhost:8088/SharekiData/user/";
 		
        url+=userId;
        url+="/";
        url+="passwd";
        url+="/";
        url+=sha256(passwd);
        
     	List<Sharekiuser> ulist=new ArrayList<Sharekiuser>();
     	//Sharekiuser[] list=restTemplate.getForObject(url, Sharekiuser[].class);
     	ResponseEntity<Sharekiuser> entity=restTemplate.getForEntity(url, Sharekiuser.class);
     	Sharekiuser user = entity.getBody();
     	
     	//if(list!=null && list.length>0)
 			//return true;
 		if(user!=null)
 		{
 			System.out.println("Login Successfull:userId:");
 			return true;
 		}
 			
 		
		}
     	catch (HttpClientErrorException e)
        {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
			
            System.out.println("HttpClientErrorException:"+e.getMessage());
           
        }
        catch(Exception e)
        {
        	
            System.out.println("RestClientUtil:loginUser:Exception:"+e.getMessage());
        }
		System.out.println("RestClientUtil:loginUser:User Not Found:"+userId);
 			return false;
	}
	
	
	public static boolean isValidNewsContent(String userId,String httpFilePath,String dataDescription,String strSelectedCountry,String strSelectedCity,String strSelectedVillage)
	{
		if(userId==null || userId.equalsIgnoreCase("") || httpFilePath==null || httpFilePath.equalsIgnoreCase("") || strSelectedCountry==null || strSelectedCountry.equalsIgnoreCase("") || strSelectedCity==null || strSelectedCity.equalsIgnoreCase("") || strSelectedVillage==null || strSelectedVillage.equalsIgnoreCase("") || dataDescription==null || dataDescription.equalsIgnoreCase("") )
		{
			
			System.out.println("RestClientUtil:isValidNewsContent: Invalid Content");
			System.out.println("UserId:"+userId+":httpFilepath:"+httpFilePath+":dataDescr:"+dataDescription+":selectedCountry"+strSelectedCountry+":selectedCity"+strSelectedCity+":selectedVillage:"+strSelectedVillage);
			return false;
		}
		return true;
	}
	
	public static boolean isValidEHelpContent(String title,String description,String strCountrySelected,String strCitySelected,String strVillageSelected)
	{
		if(title==null || title.equalsIgnoreCase("") || description==null || description.equalsIgnoreCase("") || strCountrySelected==null || strCountrySelected.equalsIgnoreCase("") || strCitySelected==null || strCitySelected.equalsIgnoreCase("") || strVillageSelected==null || strVillageSelected.equalsIgnoreCase("")  )
		{
			
			System.out.println("RestClientUtil:isValidEHepContent: Invalid Content");
			
			return false;
		}
		return true;
	}
	
	public  boolean addSharekiEHelp(String userId,String passwd,String title,String description,String strCountrySelected,String strCitySelected,String strVillageSelected)
	{
 		System.out.println("RestClientUtil:addSharekiEHep:Entered");

		if(!isValidEHelpContent(title,description,strCountrySelected,strCitySelected,strVillageSelected))
			return false;
		Sharekiuser user=null;
		user=getUserByUserId(userId,sha256(passwd));
		if(user==null)
		{
	 		System.out.println("RestClientUtil:addSharekinews:Exited As User Not Found:");

			return false;
		}
		
			//	RestTemplate restTemplate = new RestTemplate();
	        //    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	        //    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
			//add/ehelp/country/{countryName}/city/{cityName}/village/{villageName}/title/{title}/news/{news}/who/{userPK}
			
	         	String url = "http://localhost:8088/SharekiData/add/ehelp/country/";
	     		url+=strCountrySelected;
	         	url+="/";
	         	url+="city";
	         	url+="/";
	         	url+=strCitySelected;
	         	url+="/";
	         	url+="village";
	         	url+="/";
	         	url+=strVillageSelected;
	         	url+="/";
	         	url+="title";
	         	url+="/";
	         	url+=title;
	         	url+="/";
	         	url+="news";
	         	url+="/";
	         	url+=description;
	         	url+="/";
	         	url+="who";
	         	url+="/";
	         	url+=user.getId();
	         	
	         	System.out.println("EHep Path:"+url);
	    try{
	         	ResponseEntity<Sharekinews> entity=restTemplate.getForEntity(url, Sharekinews.class);
	     		Sharekinews news=entity.getBody();
	     		
	     		if(entity!=null && news!=null)
	     			return true;
	    }
	    catch(RuntimeException ee)
	    {
	    	System.out.println("RestClientutilException1:"+ee.getLocalizedMessage());
	    	System.out.println("RestClientutilException2:"+ee.getMessage());
	    	System.out.println("RestClientutilException3:"+ee.fillInStackTrace());
	    	System.out.println("RestClientutilException2:"+ee.getSuppressed());
	    	System.out.println("RestClientutilException2:"+ee.getStackTrace());
	    	
	    }
	    
	    System.out.println("RestClientUtil:addSharekiEHep:Either Response Entity Or Body is null");
 		return false;
	}
	
	public  boolean addSharekinews(String userId,String passwd,String ip,String port,String context,String dir,String filename,String dataDescription,String strSelectedCountry,String strSelectedCity,String strSelectedVillage)
	{
 		System.out.println("RestClientUtil:addSharekinews:Entered");

		if(!isValidNewsContent(userId,filename,dataDescription,strSelectedCountry,strSelectedCity,strSelectedVillage))
			return false;
		
		Sharekiuser user=null;
		user=getUserByUserId(userId,sha256(passwd));
		if(user==null)
		{
	 		System.out.println("RestClientUtil:addSharekinews:Exited As User Not Found:");

			return false;
		}
		
		//	RestTemplate restTemplate = new RestTemplate();
        //    restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        //    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            //addnews/country/{countryName}/city/{cityName}/village/{villageName}/news/{news}/image/{image}/who/{userPK}", method = RequestMethod.GET)
	 
		
	         	String url = "http://localhost:8088/SharekiData/addnews/country/";
	     		url+=strSelectedCountry;
	         	url+="/";
	         	url+="city";
	         	url+="/";
	         	url+=strSelectedCity;
	         	url+="/";
	         	url+="village";
	         	url+="/";
	         	url+=strSelectedVillage;
	         	url+="/";
	         	url+="news";
	         	url+="/";
	         	url+=dataDescription;
	         	url+="/";
	         	url+="image";
	         	url+="/";
	         	
	         	url+="ip";
	         	url+="/";
	         	url+=ip;
	         	url+="/";
	         	url+="port";
	         	url+="/";
	         	url+=port;
	         	url+="/";
	         	
	         	url+="context";
	         	url+=context;//context comes with /
	         	url+="/";
	         	url+="dir";
	         	url+="/";
	         	url+=dir;
	         	url+="/";
	         	url+="filename";
	         	url+="/";
	         	url+=filename;
	         	url+="/";
	         	url+="who";
	         	url+="/";
	         	url+=user.getId();
	    
	    
	         	
	         	System.out.println("News Path:"+url);
	    try{
	         	ResponseEntity<Sharekinews> entity=restTemplate.getForEntity(url, Sharekinews.class);
	     		Sharekinews news=entity.getBody();
	     		
	     		if(entity!=null && news!=null)
	     			return true;
	    }
	    catch(RuntimeException ee)
	    {
	    	System.out.println("RestClientutilException1:"+ee.getLocalizedMessage());
	    	System.out.println("RestClientutilException2:"+ee.getMessage());
	    	System.out.println("RestClientutilException3:"+ee.fillInStackTrace());
	    	System.out.println("RestClientutilException2:"+ee.getSuppressed());
	    	System.out.println("RestClientutilException2:"+ee.getStackTrace());
	    	
	    }
	    
	    System.out.println("RestClientUtil:addSharekinews:Either Response Entity Or Body is null");
 		return false;
		
	}


	public static boolean isValidUserContent(String userId,String passwd,String email,String citizenship,String firstname,String lastname)
	{
		if(email==null || email.equalsIgnoreCase("") || userId==null || userId.equalsIgnoreCase("") || passwd==null || passwd.equalsIgnoreCase("") || citizenship==null || citizenship.equalsIgnoreCase("") || firstname==null || firstname.equalsIgnoreCase("") || lastname==null || lastname.equalsIgnoreCase("") )
		{
			System.out.println("isValidUserContent");
			return false;
		}
		return true;
	}
	
	
	public int addSignupuser(String userId,String passwd,String email,String citizenshipPK,String firstname,String lastname,String phone)
	{
		if(!isValidUserContent(userId,passwd,email,citizenshipPK,firstname,lastname))
			return 0;//invalid content
	
		String passhash=sha256(passwd);
		System.out.println("addSignupuser:userid:"+email+"::pass:"+passhash+"::email:"+email+"::firstname:"+firstname+"::lastname:"+lastname);
		
		//user=getUserByUserId(userId,passhash);
		//if(user!=null)
		//	return 2;//already exists
		//Sharekiuser[] userlist = null;
		List<Sharekiuser> userList=new ArrayList<Sharekiuser>();
        
		try{
		//	RestTemplate restTemplate = new RestTemplate();
		//	MappingJacksonHttpMessageConverter mapper=new MappingJacksonHttpMessageConverter();
			
		//	restTemplate.getMessageConverters().add(mapper);
			  
		//	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		//	restTemplate.getMessageConverters().add(new FormHttpMessageConverter());//newly added
	  	    
			//add/user/firstname/{firstName}/lastname/{lastName}/citizenship/{citizenshipPK}/email/{email}/passwd/{passwd}/userid/{userid}/phone/{phone}", method = RequestMethod.GET)
	        
	         	String url = "http://localhost:8088/SharekiData/add/user/userid/";
	         	url+=userId;
	         	url+="/";
	         	url+="firstname";
	         	url+="/";
	         	url+=firstname;
	         	url+="/";
	         	url+="lastname";
	         	url+="/";
	         	url+=lastname;
	         	url+="/";
	         	url+="citizenship";
	         	url+="/";
	         	url+=citizenshipPK;
	         	url+="/";
	         	url+="email";
	         	url+="/";
	         	url+=email;
	         	url+="/";
	         	url+="passwd";
	         	url+="/";
	         	url+=sha256(passwd);     	
	         	if(restTemplate==null)
	         		System.out.println("RestClientUtil:addSignupdata::OOPS RESTTEMPLATE ==NULL");
	         	
	         	ResponseEntity<Sharekiuser> entity=restTemplate.getForEntity(url, Sharekiuser.class);
	         	Sharekiuser user = entity.getBody();
	            if(entity==null)
	     	    	System.out.println("RestClientUtil:addSignupdata::getForEntity Returned NULL");
	          
	            if(entity!=null)
	            	user=entity.getBody(); 	
	            if(user!=null)
	    		{
	    			System.out.println("RestCLlientUtil:addSignupUser:Success:UserAdded:"+user.getUserid());
	    			return 1;
	    		}
	    		else
	    		{
	    			System.out.println("RestCLlientUtil:addSignupUser:FAILED:getEntity returned NULL:"+userId);
	    			return 0;
	    		}
	            //user=restTemplate.getForObject(url, Sharekiuser.class);
	         	//restTemplate.postForObject(url, user, Sharekiuser.class)
		}
		catch (HttpClientErrorException e)
        {
            /**
             *
             * If we get a HTTP Exception display the error message
             */
		    System.out.println("RestClientUtil:addSignupdata:HttpClientErrorException:"+e.getMessage());
           
        }
        catch(Exception e)
        {
            System.out.println("RestClientUtil:addSignupdata:Exception:"+e.getMessage());
        }
			
		//if(userList!=null && userList.size()>0)
		//{
		//	System.out.println("RestCLientUtil:addSignupUser:UserListSize:"+userList.size());
		//	return 1;
		//}
		
		
	return 0;
		
	}

	public static String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}

}
