package com.shareki.beans.context.mvc.models;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UploadFileValidator implements Validator{
	 
	@Override
	public boolean supports(Class clazz) {
		//just validate the FileUpload instances
		return FileUploadItem.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		FileUploadItem file = (FileUploadItem)target;
 
		if(file==null)
			errors.rejectValue("file", "file=null");
		if(file.getFileData().getSize()==0){
			errors.rejectValue("file", "required.fileUpload");
		}
	}
}
