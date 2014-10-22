# Internet Pharmacy training project

## What does it look like?
You can try deployed application here: http://ipharm-biol.rhcloud.com

## Running Internet Pharmacy locally
```
	mvn tomcat7:run
```

You can then access Internet Pharmacy here: http://localhost:9966/

**Please be informed** what email sending will not work by default when you try application locally. That is because you need specify the following parameters before running the application in the `src/main/resources/email/email-config.properties`:

`mail.server.username=<your_email_here>`

`mail.server.password=<password_for_your_email>`

## Technologies used in the project

**Java:** Spring MVC, Spring Security, Spring Data JPA, Hibernate ORM, Maven, JavaMail, log4j, JUnit, Mockito, EqualsVerifier, JaCoCo.

**UI:** Thymeleaf, Twitter Bootstrap framework, TinyMCE.

**DB:** MySQL, H2.

**Tools:** Git, phpMyAdmin, OpenShift cloud hosting.

## Tomcat (JBossEWS) documentation
The OpenShift `jbossews` cartridge documentation can be found at:

https://github.com/openshift/origin-server/tree/master/cartridges/openshift-origin-cartridge-jbossews/README.md
