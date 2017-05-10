package com.shareki.beans.context.mvc.view;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class UploadedObjectView extends AbstractView {
    //To rediect to another page, with inline text.
    protected void renderMergedOutputModel1(Map model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
          byte[] bytes = (byte[]) model.get("data");
          String contentType = (String) model.get("contentType");
          response.setContentType(contentType);
          response.setContentLength(bytes.length);
          ServletOutputStream out = response.getOutputStream();
          out.write(bytes);
          out.flush();
    }
    //For a download option
    @Override
    protected void renderMergedOutputModel(Map model,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] bytes = (byte[]) model.get("data");
        String contentType = (String) model.get("contentType");
        response.addHeader("Content-disposition","attachment; filename="+model.get("filename"));
        response.setContentType(contentType);
        response.setContentLength(bytes.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.flush();
    }
}