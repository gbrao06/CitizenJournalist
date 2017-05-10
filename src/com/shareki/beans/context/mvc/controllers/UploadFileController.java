package com.shareki.beans.context.mvc.controllers;

import javax.servlet.ServletConfig;
import java.io.FileOutputStream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.http.*;

import com.shareki.beans.context.mvc.models.FileUploadItem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @ModelAttribute.

The thing you need to understand is how Spring resolves your handler method parameters and injects arguments. It uses the HandlerMethodArgumentResolver interface to do so. There are a number of implementing classes (see javadoc) and each has the responsibility to resolveArgument() by returning the argument that Spring will use to invoke() your handler method through reflection. Spring will only call the resolveArgument() method if the HandlerMethodArgumentResolver supportsParameter() method returns true for the specific parameter.

The HandlerMethodArgumentResolver implementation in question here is ServletModelAttributeMethodProcessor which extends from ModelAttributeMethodProcessor which states
 */

@SuppressWarnings("deprecation")
@Controller
@RequestMapping(value = "/uploadfile")
public class UploadFileController extends SimpleFormController{
        
	
    private String uploadFolderPath;
    ServletConfig config;

    public UploadFileController(){
		//setCommandClass(UploadItem.class);
		//setCommandName("uploadItem");
		
	}
    
    public String getUploadFolderPath() {
            return uploadFolderPath;
    }

    public void setUploadFolderPath(String uploadFolderPath) {
            this.uploadFolderPath = uploadFolderPath;
    }

    @Override
	protected ModelAndView onSubmit(HttpServletRequest request,
		HttpServletResponse response, Object command, BindException errors)
		throws Exception {
 
		FileUploadItem file = (FileUploadItem)command;
		if(file==null)
			System.out.println("command-UploadItem=NULL");
		MultipartFile multipartFile = file.getFileData();
		if(multipartFile==null)
			System.out.println("multipartfile=NULL");
		
		String fileName="";
 
		if(multipartFile!=null){
			fileName = multipartFile.getOriginalFilename();
			//do whatever you want
		}
		
		request.getSession().setAttribute("uploadFile", file);
		
		return new ModelAndView("uploadfileindex","fileName",fileName);
	}
    

    
	//called on submitting the form data
        @RequestMapping(method = RequestMethod.GET)
        public String getUploadForm(Model model) {
        	System.out.println("UploadItem Attribute is Added to model inside getUploadForm");
                //model.addAttribute(new UploadItem());
        		//<form> ModelAttribute will refer to uploadItem and bind to te parameters
                model.addAttribute("uploadItem", new FileUploadItem());
                return "/uploadfile";
        }
        
        /*
         * Spring uses an interface called HandlerMethodArgumentResolver to resolve the parameter in your handler methods and construct an object to pass as an argument.		
		If it doesn't find one, it passes null (I have to verify this)
		The BindingResult is a result object that holds errors that may have come up validating a @ModelAttribute, @Valid, @RequestBody or @RequestPart, so you can only use it with parameters that are annotated as such. There are HandlerMethodArgumentResolver for each of those annotations.
         */
        
        @RequestMapping(method = RequestMethod.POST)
        public String create(@ModelAttribute("uploadItem") FileUploadItem uploadItem, BindingResult result,
        		 HttpServletRequest request, HttpServletResponse response,
                        HttpSession session) {
        	//model.addAttribute("newItem", uploadItem);    
        	if (result.hasErrors()) {
                        for (ObjectError error : result.getAllErrors()) {
                                System.err.println("Error: " + error.getCode() + " - "
                                                + error.getDefaultMessage());
                        }
                        return "/uploadfile";
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
                                if (file.getSize() > 100000) {
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
                                byte[] buffer = new byte[100000];
                                while ((readBytes = inputStream.read(buffer, 0, 100000)) != -1) {
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
                        
                        session.setAttribute("fileName", filePath);
                        
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return "redirect:/uploadfileindex";
        }
/*
        @InitBinder
        public void initBinder(WebDataBinder binder)
        {
            binder.setAllowedFields("fileData", "fileName");
        }
  */
        
}
