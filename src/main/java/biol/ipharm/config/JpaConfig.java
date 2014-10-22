package biol.ipharm.config;

import java.lang.reflect.Field;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Olga
 */
@Configuration
@EnableJpaRepositories("biol.ipharm.dao")
@EnableTransactionManagement
public class JpaConfig {

    private static final Logger LOG = Logger.getLogger(JpaConfig.class);

    @Bean
    public DataSource dataSource() {
        try {
            return getMysqlDataSource();
        } catch (NamingException ex) {
            LOG.error("The name \"java:comp/env/jdbc/MysqlDS\" not found. In-memory H2 database will be used instead.");
            return getInMemoryDataSource();
        }
    }

    public DataSource getMysqlDataSource() throws NamingException {
        Context ctx = new InitialContext();
        DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MysqlDS");

        // OpenShift MySQL server configuration issue workaround.
        // See https://bugzilla.redhat.com/show_bug.cgi?id=1010582
        // and https://access.redhat.com/documentation/en-US/OpenShift_Online/2.0/html/User_Guide/Accessing_a_Database_Cartridge2.html
        Class<?> c = dataSource.getClass();
        try {
            Field url = c.getDeclaredField("url");
            url.setAccessible(true);
            String currentUrl = (String) url.get(dataSource);
            String properUrlEnding = "?useUnicode=yes&characterEncoding=UTF-8";
            url.set(dataSource, currentUrl + properUrlEnding);

            // The following code helps to keep track whether the URL has been changed:
            // url = c.getDeclaredField("url");
            // url.setAccessible(true);
            // LOG.info("Initial URL: " + currentUrl);
            // LOG.info("Proper URL: " + url.get(dataSource));
        } catch (NoSuchFieldException ex) {
            LOG.error("Field \"url\" couldn't be found in the datasource object.", ex);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            LOG.error("Illegal argument or access to the field \"url\" in the datasource object.", ex);
        }
        // End of workaround.

        return dataSource;
    }

    public DataSource getInMemoryDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("classpath:setup/ipharm_in_memory.sql")
                .build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        return jpaVendorAdapter;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan("biol.ipharm.entity");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
}
