package biol.ipharm.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 *
 * @author Olga
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/
    // http://docs.spring.io/spring-security/site/docs/3.2.x/guides/

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(getUserQuery())
                .authoritiesByUsernameQuery(getAuthoritiesQuery());
    }

    private String getUserQuery() {
        // For example, "select username, password, enabled from users where username = ?"
        // Or with hardcoded 'enabled/disabled': "select username, password, true from spitter where username = ?"
        return "SELECT login as username, password as password, true "
                + "FROM customer "
                + "WHERE login = ?";
    }

    private String getAuthoritiesQuery() {
        // For example, "select username, authority from authorities where username = ?"
        // Or with hardcoded role: "select username,'ROLE_SPITTER' from spitter where username = ?"
        return "SELECT login as username, authority "
                + "FROM customer "
                + "WHERE login = ?";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                // set permitAll for all URLs associated with Form Login
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Spring Security should completely ignore URLs starting with /resources/
        web.ignoring()
                .antMatchers("/resources/**");
    }
}
