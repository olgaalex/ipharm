package biol.ipharm.config;

import java.util.Set;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Olga
 */
public class WebAppInitializer implements WebApplicationInitializer {

    private static final Logger LOG = Logger.getLogger(WebAppInitializer.class);

    // По материалам http://www.rockhoppertech.com/blog/spring-mvc-configuration-without-xml/
    // GitHub: https://github.com/genedelisa/spring-mvc3-javaconfig
    // и (Thymeleaf) http://javaee.ch/2013/11/02/basic-spring-mvc-annotation-based-configuration-example-with-thymeleaf-and-no-xml/
    // GitHub: https://github.com/marco76/minimvc
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfig.class);

        // Glue WebApplicationContext to the lifecycle of ServletContext
        // Or another explanation - Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic ipharmServlet
                = servletContext.addServlet("ipharmServlet", new DispatcherServlet(rootContext));
        ipharmServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = ipharmServlet.addMapping("/");

        if (!mappingConflicts.isEmpty()) {
            for (String s : mappingConflicts) {
                LOG.error("Mapping conflict: " + s);
            }
            throw new IllegalStateException("'ipharmServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
        }

        // Adding CharacterEncodingFilter
        FilterRegistration.Dynamic filterRegistration
                = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
    }
}
