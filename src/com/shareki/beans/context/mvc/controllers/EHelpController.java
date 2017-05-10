package com.shareki.beans.context.mvc.controllers;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.shareki.beans.context.mvc.models.EHelpContainer;
import com.shareki.beans.context.mvc.models.SharekiDataSelector;

//This is Spring MVC model


@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/ehelp")
public class EHelpController extends SimpleFormController{

	//Either we can return a String or ModelView object which will have an attribute with URL
	//String is name of the page
	
/*	@Override
	protected Map<?, ?> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
	
	 	System.out.println("Called refernceData"); 
	    
	 	EHelpContainer ehep = EHelpContainer.getEHelpContainer(null,null,null);
		//dataSelector.init();
		// ...populate your custom collection
	   request.setAttribute("eHelpContainer", ehep);
	  
	  return super.referenceData(request, command, errors);
	}
*/	
	//default call 
	//Get is to populate data in the form
	@RequestMapping(method = RequestMethod.GET)
     public String getEHelpForm(Model model,HttpServletRequest request, 
    	        HttpServletResponse response) {
     	
		System.out.println("Get:getEHelpForm");
		
		String selectedCity=(String) request.getSession().getAttribute("selectedCity");
    	
    	String selectedVillage=(String) request.getSession().getAttribute("selectedVillage");
    	
    	String selectedCountry=(String) request.getSession().getAttribute("selectedCountry");
    	
    	//String dataDescription=(String) request.getSession().getAttribute("description");
    	
     	//System.out.println("Get:EHelps:After:Selected City:"+SharekiDataSelector.getDataSelector().getStrSelectedCity()); 
     	//System.out.println("Get:EHelps:After:Selected Village:"+SharekiDataSelector.getDataSelector().getStrSelectedVillage()); 
     	//System.out.println("Get:EHelps:After:dataDescription:"+SharekiDataSelector.getDataSelector().getDataDescription()); 
    	
     	System.out.println("GET:EHelps:login from Session:"+request.getSession().getAttribute("userId")); 
     	EHelpContainer ehelp=new EHelpContainer();
    	
        System.out.println("EHelps:InCountry Size:"+ehelp.getAllNews());
        System.out.println("EHelps:InCity Size:"+ehelp.getCityNews());
        
        System.out.println("EHelps:InVillage Size:"+ehelp.getVillageNews());
        model.addAttribute("eHelpContainer",ehelp);
        
    	//RedirectAttributes  aa;
    	//redirectAttributes.addFlashAttribute("mapping1Form", mapping1FormObject);
    	
    	//create the new object
        return "/ehelp";
     }

	
    @RequestMapping(method = RequestMethod.POST)
    public String postEHelpForm(@ModelAttribute("eHelpContainer") EHelpContainer eHelpContainer, BindingResult result,
    		 HttpServletRequest request, HttpServletResponse response,
                    HttpSession session) {
    	//model.addAttribute("newItem", uploadItem);    
    	System.out.println("POST:posteHelpContrerForm"); 
       
    	System.out.println("POST:Selected Village:"+eHelpContainer.getDataSelector().getStrSelectedVillage()); 
    	System.out.println("POST:Selected City:"+eHelpContainer.getDataSelector().getStrSelectedCity()); 
    	System.out.println("POST:Selected Country:"+eHelpContainer.getDataSelector().getStrSelectedCountry()); 
    	
    	System.out.println("POST:login:"+session.getAttribute("userId"));
    	
    	if (result.hasErrors()) {
                    for (ObjectError error : result.getAllErrors()) {
                            System.err.println("Error: " + error.getCode() + " - "
                                            + error.getDefaultMessage());
                    }
                    return "/ehelp";
            }
    	                 
                   System.out.println("Data Descriptin:"+eHelpContainer.getDataSelector().getDataDescription());
                   
                   session.setAttribute("selectedCountry", eHelpContainer.getDataSelector().getStrSelectedCountry());
                   session.setAttribute("selectedCity",eHelpContainer.getDataSelector().getStrSelectedCity());
                   session.setAttribute("selectedVillage",eHelpContainer.getDataSelector().getStrSelectedVillage());
                   session.setAttribute("dataDescription",eHelpContainer.getDataSelector().getDataDescription());
                   
                   if(eHelpContainer.addSharekiEHelp((String)session.getAttribute("userId"),(String)session.getAttribute("passwd")))
                   {
                	   //remove all unnecessary attributes.
                	   //session.removeAttribute("selectedCountry");
                	   //session.removeAttribute("selectedCity");
                	   //session.removeAttribute("selectedVillage");
                	   //session.removeAttribute("dataDescription");
                	   	
                	   return "redirect:/ehelpvillagestories";
                   }      
             		 
            
            return "redirect:/ehelp";	
    }
    
     /*
      * Spring uses an interface called HandlerMethodArgumentResolver to resolve the parameter in your handler methods and construct an object to pass as an argument.		
		If it doesn't find one, it passes null (I have to verify this)
		The BindingResult is a result object that holds errors that may have come up validating a @ModelAttribute, @Valid, @RequestBody or @RequestPart, so you can only use it with parameters that are annotated as such. There are HandlerMethodArgumentResolver for each of those annotations.
      */
     /*
     @RequestMapping(method = RequestMethod.POST)
     public String create(@ModelAttribute("uploadFileAttribute") FileUploadItem uploadItem, BindingResult result,
     		 HttpServletRequest request, HttpServletResponse response,
                     HttpSession session) {
     	//model.addAttribute("newItem", uploadItem);    
     	if (result.hasErrors()) {
                     for (ObjectError error : result.getAllErrors()) {
                             System.err.println("Error: " + error.getCode() + " - "
                                             + error.getDefaultMessage());
                     }
                     return "/shareki";
             }

             // Some type of file processing...
             System.out.println("--------file processing--------------");
             try {
                     MultipartFile file = uploadItem.getFileData();
              //       if(file==null)
              //       	file=uploadFile;
                     
                     if(file==null){
                     	System.err.println("uploadItem.getFileData returned null");
                     	
                     }
                     String fileName = null;
                     InputStream inputStream = null;
                     OutputStream outputStream = null;
                     
                     if (file.getSize() > 0) {
                             inputStream = file.getInputStream();
                             if (file.getSize() > 1000000) {
                                     System.out.println("File Size:::" + file.getSize());
                                     return "/uploadfile";
                             }
                             System.out.println("size::" + file.getSize());
                             
                             
                             String imagesPath="/images";
                             String imagesRealPath=request.getRealPath(imagesPath);
                             imagesRealPath+="\\";
                             System.out.println("imagesRealPath:" +imagesRealPath);
                             fileName=imagesRealPath+file.getOriginalFilename();
                             
                             //fileName = contextPath+ "/images/"
                               //              + file.getOriginalFilename();
                             
                             System.out.println("fullPath:" + fileName);
                             
                             outputStream = new FileOutputStream(fileName);
                            
                             System.out.println("contextPath:" + request.getContextPath());

                             System.out.println("fileName:" + file.getOriginalFilename());

                             int readBytes = 0;
                             byte[] buffer = new byte[1000000];
                             while ((readBytes = inputStream.read(buffer, 0, 1000000)) != -1) {
                                     outputStream.write(buffer, 0, readBytes);
                             }
                             outputStream.flush();
                             outputStream.close();
                            
                             inputStream.close();
                     }
                     
                    //set file name attribute for URI to be displayed for the client 
                   String filePath = request.getContextPath()+ "/images/"
                                  + file.getOriginalFilename();
                   
                   
                     // ..........................................
                     
                     session.setAttribute("uploadedFileName", filePath);
                     
             } catch (Exception e) {
                     e.printStackTrace();
             }
             return "redirect:/shareki";
     }
     */
	
     /*@RequestMapping(value="/imageDownloadFile")
     	public ModelAndView downloadFile(@RequestParam("empId") long empId){
	        Map model = new HashMap();
	        //User user = userService.getUploadedFileForEmployee(empId);
	        //model.put("data", empMap.getFileDataBytes());
	        //model.put("contentType", empMap.getFileContentType());
	        //model.put("filename", empMap.getFileName());
	        return new ModelAndView(uploadedObjectView, model);
    }
     */
	 
}
