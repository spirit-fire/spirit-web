package com.spirit.api.servlet;

import com.spirit.commons.common.confs.ApplicationContextHolder;
import com.spirit.commons.common.confs.ConfigLoader;
import com.spirit.commons.common.confs.ConfigLoaderFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SpiritContextLoaderListener implements ServletContextListener {

    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    public static final ConfigLoader configLoader = ConfigLoaderFactory.getConfigLoader();

    public SpiritContextLoaderListener(){
        super();
    }

    /**
     * * Notification that the web application initialization
     * * process is starting.
     * * All ServletContextListeners are notified of context
     * * initialization before any filter or servlet in the web
     * * application is initialized.
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String configLocation = sc.getInitParameter(CONFIG_LOCATION_PARAM);
        try{
            XmlWebApplicationContext ctx = new XmlWebApplicationContext();
            ctx.setParent(null);
            ctx.setServletContext(sc);
            //是否允许 bean overriding, 默认允许
            ctx.setAllowBeanDefinitionOverriding(true);
            ctx.setConfigLocation(configLocation);
            ctx.refresh();
            ApplicationContextHolder.setApplicationContext(ctx);
        }
        catch(RuntimeException e){
            e.printStackTrace();
            throw e;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * * Notification that the servlet context is about to be shut down.
     * * All servlets and filters have been destroy()ed before any
     * * ServletContextListeners are notified of context
     * * destruction.
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        if(ApplicationContextHolder.getModules() != null){
            for (ConfigurableApplicationContext ctx : ApplicationContextHolder.getModules().values()) {
                ctx.close();
            }
        }
        ApplicationContextHolder.getApplicationContext().close();
    }
}
