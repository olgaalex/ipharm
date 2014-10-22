package biol.ipharm.config;

import biol.ipharm.entity.formatter.PharmaceuticalFormFormatter;
import biol.ipharm.entity.formatter.ProducerFormatter;
import biol.ipharm.entity.formatter.ProductGroupFormatter;
import biol.ipharm.entity.formatter.ProductSubgroupFormatter;
import static biol.ipharm.util.Utils.MAX_UPLOAD_FILE_SIZE;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author Olga
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"biol.ipharm.controller"})
class WebMvcConfig extends WebMvcConfigurerAdapter {

    // Template Resolver for email templates
    @Bean
    public TemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
        emailTemplateResolver.setPrefix("email/template/");
        emailTemplateResolver.setTemplateMode("HTML5");
        // In TemplateResolver you set the character encoding in which your 
        // files ARE in disk, this is, the one that should be used for reading them 
        // and converting their bytes into chars (characters are encoding-independent).
        emailTemplateResolver.setCharacterEncoding("UTF-8");
        // Template cache is true by default. Set to false if you 
        // want templates to be automatically updated when modified.
        emailTemplateResolver.setCacheable(false);
        emailTemplateResolver.setOrder(1);
        return emailTemplateResolver;
    }

    // Template Resolver for webapp pages
    @Bean
    public TemplateResolver webTemplateResolver() {
        ServletContextTemplateResolver webTemplateResolver = new ServletContextTemplateResolver();
        webTemplateResolver.setPrefix("/WEB-INF/pages/");
        webTemplateResolver.setSuffix(".html");
        webTemplateResolver.setTemplateMode("HTML5");
        // In TemplateResolver you set the character encoding in which your 
        // files ARE in disk, this is, the one that should be used for reading them 
        // and converting their bytes into chars (characters are encoding-independent).
        webTemplateResolver.setCharacterEncoding("UTF-8");
        // Template cache is true by default. Set to false if you 
        // want templates to be automatically updated when modified.
        webTemplateResolver.setCacheable(false);
        webTemplateResolver.setOrder(2);
        return webTemplateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolvers(templateResolvers());

        // For Spring Security
        templateEngine.setAdditionalDialects(additionalDialects());

        return templateEngine;
    }

    private Set<? extends ITemplateResolver> templateResolvers() {
        Set<TemplateResolver> templateResolvers = new HashSet<>();
        templateResolvers.add(emailTemplateResolver());
        templateResolvers.add(webTemplateResolver());
        return templateResolvers;
    }

    private Set<IDialect> additionalDialects() {
        Set<IDialect> additionalDialects = new HashSet<>();
        additionalDialects.add(new SpringSecurityDialect());
        return additionalDialects;
    }

    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        // In ThymeleafViewResolver you set the character encoding in which 
        // your processed pages should be written into the HTTP response's 
        // output stream, this is, the one that should be used for converting 
        // your characters back into bytes for transferring them over the network.
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/robots.txt").addResourceLocations("/robots.txt");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(productGroupFormatter());
        registry.addFormatter(pharmaceuticalFormFormatter());
        registry.addFormatter(producerFormatter());
        registry.addFormatter(productSubgroupFormatter());
    }

    @Bean
    public Formatter productGroupFormatter() {
        return new ProductGroupFormatter();
    }

    @Bean
    public Formatter pharmaceuticalFormFormatter() {
        return new PharmaceuticalFormFormatter();
    }

    @Bean
    public Formatter producerFormatter() {
        return new ProducerFormatter();
    }

    @Bean
    public Formatter productSubgroupFormatter() {
        return new ProductSubgroupFormatter();
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_FILE_SIZE);
        return multipartResolver;
    }
}
