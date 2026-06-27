package com.cognizant.spring.di;

import com.cognizant.spring.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 *
 * - Constructor Injection
 * - Setter Injection
 * - Field Injection (@Autowired)
 * - @Component, @Service, @Repository, @Controller stereotypes
 * - @Configuration + @Bean (Java-based config)
 * - @Qualifier for resolving ambiguity
 * - @Value for properties
 */
class BankBean {
    private String bankName;
    private String bankCode;
    private MessageService messageService;

    // Constructor Injection — dependencies passed via constructor
    public BankBean(String bankName, String bankCode, MessageService messageService) {
        this.bankName      = bankName;
        this.bankCode      = bankCode;
        this.messageService = messageService;
    }

    public void showBankInfo() {
        System.out.println("Bank: " + bankName + " [" + bankCode + "]");
        System.out.println("Message: " + messageService.getMessage());
    }
}
@Component("accountRepository")
class AccountRepository {
    public String findAccount(long id) {
        return "Account-" + id + " [Mani, ₹150000]";
    }
}

@Service("accountService")
class AccountService {

    // Field Injection (quick but harder to test — prefer constructor)
    @Autowired
    private AccountRepository accountRepository;

    @Value("${app.bank.name:Cognizant Bank}")  // with default value
    private String bankName;

    public String getAccountDetails(long customerId) {
        System.out.println("Bank: " + bankName);
        return accountRepository.findAccount(customerId);
    }
}
@Configuration
@ComponentScan(basePackages = "com.cognizant.spring")
class AppConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepository();
    }

    @Bean
    public AccountService accountService() {
        AccountService service = new AccountService();
        return service;
    }

    @Bean("emailNotifier")
    public MessageService emailMessageService() {
        // Would return EmailMessageService instance
        return () -> "Email configured via @Bean";
    }

    @Bean("smsNotifier")
    public MessageService smsMessageService() {
        return () -> "SMS configured via @Bean";
    }
}
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        // Java-based config
        var context = new org.springframework.context.annotation
                          .AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== DEPENDENCY INJECTION DEMO ===\n");

        AccountService accountService = context.getBean(AccountService.class);
        System.out.println("Account: " + accountService.getAccountDetails(1L));

        // @Qualifier usage — multiple beans of same type
        MessageService emailService = context.getBean("emailNotifier", MessageService.class);
        MessageService smsService   = context.getBean("smsNotifier",   MessageService.class);
        System.out.println("Email: " + emailService.getMessage());
        System.out.println("SMS:   " + smsService.getMessage());

        context.close();
    }
}
