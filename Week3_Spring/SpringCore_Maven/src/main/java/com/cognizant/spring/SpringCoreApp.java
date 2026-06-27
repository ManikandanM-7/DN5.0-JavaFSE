package com.cognizant.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * - Spring IoC Container (ApplicationContext)
 * - XML-based bean configuration
 * - Loading beans from applicationContext.xml
 * - BeanFactory vs ApplicationContext difference
 *
 * Run: mvn exec:java -Dexec.mainClass="com.cognizant.spring.SpringCoreApp"
 */
public class SpringCoreApp {

    private static final Logger log = LoggerFactory.getLogger(SpringCoreApp.class);

    public static void main(String[] args) {
        log.info("Starting Spring IoC Container...");

        // Load ApplicationContext from XML config
        ApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

        log.info("Spring Container initialized successfully");

        // Ex 1: Get bean from container
        GreetingService greetingService =
            (GreetingService) context.getBean("greetingService");
        greetingService.greet("Mani");

        // Ex 2: Get typed bean (no cast needed)
        MessageService messageService =
            context.getBean("messageService", MessageService.class);
        System.out.println(messageService.getMessage());

        // Ex 2: Get bean with DI — EmailService depends on MessageService
        NotificationService notificationService =
            context.getBean("notificationService", NotificationService.class);
        notificationService.sendNotification("mani@email.com", "DN 5.0 assessment submitted!");

        // Show bean names registered in container
        System.out.println("\n--- Beans in Spring Container ---");
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("  Bean: " + beanName);
        }

        ((ClassPathXmlApplicationContext) context).close();
        log.info("Spring Container closed.");
    }
}
