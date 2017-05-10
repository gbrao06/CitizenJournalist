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

import com.shareki.beans.context.mvc.models.SharekiDataSelector;

//This is Spring MVC model


@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/shareki")
public class SharekiController extends SimpleFormController{

	//Either we can return a String or ModelView object which will have an attribute with URL
	//String is name of the page
	
	@Override
	protected Map<?, ?> referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
	
	 	System.out.println("Called refernceData"); 
	    
		SharekiDataSelector dataSelector = SharekiDataSelector.getDataSelector();
		//dataSelector.init();
		// ...populate your custom collection
	   request.setAttribute("dataSelector", dataSelector);
	  
	  return super.referenceData(request, command, errors);
	}
	
	//default call 
	//Get is to populate data in the form
	@RequestMapping(method = RequestMethod.GET)
     public String getSharekiForm(Model model,HttpServletRequest request, 
    	        HttpServletResponse response) {
     	
		System.out.println("Get:getSharekiForm");
		System.out.println("Get:Shareki:Before:Selected City:"+SharekiDataSelector.getDataSelector().getStrSelectedCity()); 
    	
		String selectedCity=(String) request.getSession().getAttribute("selectedCity");
    	SharekiDataSelector.getDataSelector().setStrSelectedCity(selectedCity);
    	
    	String selectedVillage=(String) request.getSession().getAttribute("selectedVillage");
    	SharekiDataSelector.getDataSelector().setStrSelectedVillage(selectedVillage);
    	
    	String dataDescription=(String) request.getSession().getAttribute("dataDescription");
    	SharekiDataSelector.getDataSelector().setDataDescription(dataDescription);
    	
     	System.out.println("Get:Shareki:After:Selected City:"+SharekiDataSelector.getDataSelector().getStrSelectedCity()); 
     	System.out.println("Get:Shareki:After:Selected Village:"+SharekiDataSelector.getDataSelector().getStrSelectedVillage()); 
     	System.out.println("Get:Shareki:After:dataDescription:"+SharekiDataSelector.getDataSelector().getDataDescription()); 
    	
     	System.out.println("GET:Shareki:login from Session:"+request.getSession().getAttribute("userId")); 
    	

        System.out.println("Shareki:Countries Size:"+SharekiDataSelector.getDataSelector().getCountryList().size()); 
    	System.out.println("Shareki:States Size:"+SharekiDataSelector.getDataSelector().getStateList().size()); 
    	System.out.println("Shareki:Cities Size:"+SharekiDataSelector.getDataSelector().getCityList().size()); 
    	model.addAttribute("dataSelector",SharekiDataSelector.getDataSelector());
    
    	//RedirectAttributes  aa;
    	//redirectAttributes.addFlashAttribute("mapping1Form", mapping1FormObject);
    	
    	//create the new object
        return "shareki";
     }

	
    @RequestMapping(method = RequestMethod.POST)
    public String postSharekiForm(@ModelAttribute("dataSelector") SharekiDataSelector dataSelector, BindingResult result,
    		 HttpServletRequest request, HttpServletResponse response,
                    HttpSession session) {
    	//model.addAttribute("newItem", uploadItem);    
    	System.out.println("POST:postForm"); 
        
    	System.out.println("POST:Selected City:"+dataSelector.getStrSelectedCity()); 
    	System.out.println("POST:Selected Country:"+dataSelector.getStrSelectedCountry()); 
    	
    	System.out.println("POST:Cities Size:"+dataSelector.getCityList().size()); 
    	System.out.println("POST:login:"+session.getAttribute("userId"));
    	
    	if (result.hasErrors()) {
                    for (ObjectError error : result.getAllErrors()) {
                            System.err.println("Error: " + error.getCode() + " - "
                                            + error.getDefaultMessage());
                    }
                    return "/shareki";
            }
    	boolean reDitectSamePage=true;
    	byte[] buffer = new byte[5000000];
    	String filePath=null;
    	String fileServerPath=null;
    	String originalFileName=null;
            // Some type of file processing...
            System.out.println("--------file processing--------------");
            try {
                    MultipartFile file = dataSelector.getFileData();
             //       if(file==null)
             //       	file=uploadFile;
                    
                    if(file==null){
                    	System.err.println("uploadItem.getFileData returned null");
                    	
                    }
                    if(file!=null)
                    {
	                    String fileName = null;
	                    InputStream inputStream = null;
	                    OutputStream outputStream = null;
	                    
                    if ( file.getSize() > 0) {
                            inputStream = file.getInputStream();
                            if (file.getSize() > 5000000) {
                                    System.out.println("File Size:::" + file.getSize());
                                    return "/shareki";
                            }
                            System.out.println("size::" + file.getSize());
                            
                            
                            String imagesPath="/images";
                            String imagesRealPath=request.getRealPath(imagesPath);
                            imagesRealPath+="\\";
                            System.out.println("imagesRealPath:" +imagesRealPath);
                            fileName=imagesRealPath+file.getOriginalFilename();
                            
                            //fileName = contextPath+ "/images/"
                              //              + file.getOriginalFilename();
                    
                            String serverName=request.getServerName();
                            int portName=request.getServerPort();
                    
                            
                            String fileServerPath1="http";
                            fileServerPath1+=":";
                            fileServerPath1+="//";
                            
                            fileServerPath =serverName;
                            fileServerPath+=":";
                            fileServerPath+=String.valueOf(portName);
                            
                            filePath=request.getContextPath()+ "/images/"
                                         + file.getOriginalFilename();
                          fileServerPath+=filePath;
                         fileServerPath1+=fileServerPath;
                           
                         originalFileName=file.getOriginalFilename();
                         
                         System.out.println("Original File Name:" +originalFileName);
                         
                         fileServerPath=fileServerPath1;
                         System.out.println("fullPath:" + fileName);
                            System.out.println("HTTP File Path:"+filePath+"::HTTP Full Path1:"+fileServerPath);  
                            System.out.println("HTTP File Server Path1 :"+fileServerPath1);  
                                    
                            outputStream = new FileOutputStream(fileName);
                           
                            System.out.println("contextPath:" + request.getContextPath());

                            System.out.println("fileName:" + file.getOriginalFilename());

                            int readBytes = 0;
                            
                            while ((readBytes = inputStream.read(buffer, 0, 5000000)) != -1) {
                                    outputStream.write(buffer, 0, readBytes);
                            }
                            outputStream.flush();
                            outputStream.close();
                           
                            inputStream.close();
                    }
                    
                   //set file name attribute for URI to be displayed for the client 
                    
                    // ..........................................
                   session.setAttribute("uploadedFileName", fileServerPath);
                   System.out.println("getAttribute:uploadedFileName:"+fileServerPath);
                 } 
                 
                   System.out.println("Selected City:"+dataSelector.getStrSelectedCity());  
                   session.setAttribute("selectedCountry", dataSelector.getStrSelectedCountry());
                   session.setAttribute("selectedCity",dataSelector.getStrSelectedCity());
                   session.setAttribute("selectedVillage",dataSelector.getStrSelectedVillage());
                   session.setAttribute("dataDescription",dataSelector.getDataDescription());
                   
                   //if data selector has both valid image and description, then insert into the table
                   
                   String ip=request.getServerName();
                   int nport=request.getServerPort();
                   String port=String.valueOf(nport);
                   String context=request.getContextPath();
                   String dir="images";
                   
                   System.out.println("File String:IP"+ip+":PORT:"+port+":CONTEXT:"+context+":DIR:"+dir+":FILENAME:"+originalFileName);  
                   
                   if(dataSelector.addSharekinews((String)session.getAttribute("userId"),(String)session.getAttribute("passwd"),ip,port,context,dir,originalFileName))
                   {
                	   //remove all unnecessary attributes.
                	   //session.removeAttribute("selectedCountry");
                	   //session.removeAttribute("selectedCity");
                	   //session.removeAttribute("selectedVillage");
                	   session.removeAttribute("dataDescription");
                	   session.removeAttribute("uploadedFileName");
                  			
                	   return "redirect:/villagestories";
                   }      
             		 
            } catch (Exception e) {
                    e.printStackTrace();
            }
            
            return "redirect:/shareki";	
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
