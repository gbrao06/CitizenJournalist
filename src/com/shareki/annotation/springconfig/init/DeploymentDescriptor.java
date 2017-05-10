package com.shareki.annotation.springconfig.init;

import javax.servlet.FilterRegistration;
import javax.servlet.Registration.Dynamic;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRegistration;
//import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.servlet.DispatcherServlet;

import com.shareki.annotation.springconfig.RootConfig;
import com.shareki.annotation.springconfig.WebMvcConfig;

//WebApplicationInitializer is required for configuration based dipatchservlet registration to avoid xml based configuration.
//The Servlet 3.0 ServletContext API allows for setting init-params, context-params, etc programmatically

//In the example below, WEB-INF/web.xml was successfully replaced with code in the form of a WebApplicationInitializer,
//but the actual dispatcher-config.xml Spring configuration remained XML-based. WebApplicationInitializer is a perfect
//fit for use with Spring's code-based @Configuration classes. See @Configuration Javadoc for complete details, 
//but the following example demonstrates refactoring to use Spring's AnnotationConfigWebApplicationContext in lieu of
//XmlWebApplicationContext, and user-defined @Configuration classes AppConfig and DispatcherConfig instead of Spring XML files.
//This example also goes a bit beyond those above to demonstrate typical configuration of the 'root' application context and
//registration of the ContextLoaderListener:
	
//public interface ServletContext
//Defines a set of methods that a servlet uses to communicate with its servlet container, for example, to get the MIME type of a file, dispatch requests, or write to a log file.

//There is one context per "web application" per Java Virtual Machine. (A "web application" is a collection of servlets and content installed under a specific subset of the server's URL namespace such as /catalog and possibly installed via a .war file.)

//In the case of a web application marked "distributed" in its deployment descriptor, there will be one context instance for each virtual machine. In this situation, the context cannot be used as a location to share global information (because the information won't be truly global). Use an external resource like a database instead.

//The ServletContext object is contained within the ServletConfig object, which the Web server provides the servlet when the servlet is initialized.

//WebApplicationInitializer implementations are detected automatically

//RequestDispatcher getRequestDispatcher(java.lang.String path)
//Returns a RequestDispatcher object that acts as a wrapper for the resource located at the given path. 
//A RequestDispatcher object can be used to forward a request to the resource or to include the resource in a response. The resource can be dynamic or static.
//The pathname must begin with a / and is interpreted as relative to the current context root.
//Use getContext to obtain a RequestDispatcher for resources in foreign contexts.

//This method returns null if the ServletContext cannot return a RequestDispatcher.
	
public class DeploymentDescriptor implements WebApplicationInitializer{

	/*@Override
    public void onStartup(ServletContext container) {
      XmlWebApplicationContext appContext = new XmlWebApplicationContext();
      appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(appContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }
	*/
	//The dispatcher servlet is the bit that "knows" to call that method when a browser requests the page, and to combine its results with the matching JSP file to make an html document.

	//How it accomplishes this varies widely with configuration and Spring version.

	//There's also no reason the end result has to be web pages. It can do the same thing to locate RMI end points, handle SOAP requests, anything that can come into a servlet.

	//Configure the given ServletContext with any servlets, filters, listeners context-params and attributes necessary for initializing this web application
	
	@Override
    public void onStartup(ServletContext container) throws ServletException{
     
		System.out.println("Inside WebAppInitializer::onStartup");
		//{{ganji
		
		 // This is the programmatic way of declaring filters. This allows you to order
        // Filters. The order of these security filters DOES MATTER!
		//Add charhechter encoding to utf8 filter
		FilterRegistration.Dynamic fr = container.addFilter("encodingFilter",  
			      new CharacterEncodingFilter());
			   fr.setInitParameter("encoding", "UTF-8");
			   fr.setInitParameter("forceEncoding", "true");
			   fr.addMappingForUrlPatterns(null, true, "/*");
			   
		/*FilterRegistration.Dynamic mockSecurityFilter       = servletContext.addFilter ("mockSecurityFilter", "org.ghc.security.MockSecurityFilter");
        mockSecurityFilter.addMappingForUrlPatterns         (EnumSet.of (REQUEST), true, "/*");

        FilterRegistration.Dynamic siteMinderSecurityFilter = servletContext.addFilter ("siteMinderSecurityFilter", "org.ghc.security.SiteMinderSecurityFilter");
        siteMinderSecurityFilter.addMappingForUrlPatterns   (EnumSet.of (REQUEST), true, "/*");

        FilterRegistration.Dynamic userDetailsStoreFilter   = servletContext.addFilter ("userDetailsStoreFilter", "org.ghc.security.UserDetailsStoreFilter");
        userDetailsStoreFilter.addMappingForUrlPatterns     (EnumSet.of (REQUEST), true, "/*");


        // Static resource handling using "default" servlet
        servletContext.getServletRegistration ("default").addMapping ("*.js", "*.css", "*.jpg", "*.gif", "*.png");
        // Map jspf files to jsp servlet
        servletContext.getServletRegistration ("jsp").addMapping ("*.jspf");
        */
        //}}ganji
        
        
	   // Create the 'root' Spring application context
    
	  // Create the root appcontext
   AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
   rootContext.register(RootConfig.class);
   // since we registered RootConfig instead of passing it to the constructor
   rootContext.refresh(); 
 
   // Manage the lifecycle of the root appcontext. startup,shutdown
   //configuring spring in web application.
   //ContextLoaderListener is a ServletContextListener that initializes when your webapp starts up. By default, 
   //it looks for Spring’s configuration file at WEB-INF/applicationContext.xml.
   
   container.addListener(new ContextLoaderListener(rootContext));
   container.setInitParameter("defaultHtmlEscape", "true");
		
	// Create the dispatcher servlet's Spring application context
      AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
      
      //{{ganji
      dispatcherContext.setServletContext(container);
      dispatcherContext.register(WebMvcConfig.class);
      dispatcherContext.refresh();
		//}}
      dispatcherContext.register(DispatcherType.class);
      
      // Register and map the dispatcher servlet
      ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/");
    }
	
}
