package com.shareki.beans.context.mvc.models;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

//Create a MultipartFile variable to store the uploaded file. Alternatively, you can use the byte[] to store it, but i more prefer to use the MultipartFile, because it can get the uploaded file detail (file name, file size …) easily.

public class FileUploadItem {
	
        private String fileName;
        private MultipartFile fileData;

        public String getFileName() {
			return fileName;
		}

		public MultipartFile getFileData() {
			return fileData;
		}

		public void setFileName(String fileName) {
			System.out.println("UploadItem::setFileName called");
        	
			this.fileName = fileName;
		}

		public void setFileData(MultipartFile fileData) {
			System.out.println("UploadItem::setFileData-CommonsMultiPart called");
        	
			this.fileData = fileData;
		}

		public String getFilename() {
                return fileName;
        }

		/*        public void setFilename(String fileName) {
        	System.out.println("UploadItem::setFileName Called");
        	if(fileName!=null)
        		System.out.println("UploadItem::setFilename-filename not null");
        	else
        		System.out.println("UploadItem::setFilename-filename NULL");
        	
                this.fileName = fileName;
        }

        public void setFileData(MultipartFile fileData) {
        	System.out.println("UploadItem::setFileData-CommonsMultiPart called");
        	if(fileData!=null)
        		System.out.println("UploadItem::fileData Not Null");
        	else
        		System.out.println("UploadItem::fileData NULL");
        	this.fileData = fileData;
        }
  */
        
}
