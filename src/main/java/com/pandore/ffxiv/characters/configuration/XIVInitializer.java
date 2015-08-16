package com.pandore.ffxiv.characters.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//public class XIVInitializer implements WebApplicationInitializer {
@Configuration
public class XIVInitializer extends SpringBootServletInitializer  {
//@Configuration
//public class XIVInitializer implements ServletContextInitializer {

	@Override
    public void onStartup(ServletContext container) throws ServletException {
 
    	System.out.println("########## STARTUP !!!!!!!!!!!");
    	
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(XIVConfiguration.class);
        ctx.setServletContext(container);
 
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(ctx));
 
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
 
}
