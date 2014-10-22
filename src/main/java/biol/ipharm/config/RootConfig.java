package biol.ipharm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Olga
 */
@Configuration
@ComponentScan(basePackages = {"biol.ipharm.service", "biol.ipharm.dao", "biol.ipharm.order"})
@Import({JpaConfig.class, WebMvcConfig.class, SecurityConfig.class, EmailConfig.class})
public class RootConfig {

}
