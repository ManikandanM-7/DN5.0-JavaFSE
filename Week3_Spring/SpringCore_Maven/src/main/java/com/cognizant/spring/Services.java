package com.cognizant.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
class GreetingService {
    private static final Logger log = LoggerFactory.getLogger(GreetingService.class);
    private String greeting = "Hello";

    public void setGreeting(String greeting) { this.greeting = greeting; }

    public void greet(String name) {
        String msg = greeting + ", " + name + "! Welcome to ";
        log.info(msg);
        System.out.println(msg);
    }
}
interface MessageService {
    String getMessage();
}

class EmailMessageService implements MessageService {
    private String smtpHost;
    private int    smtpPort;

    public void setSmtpHost(String smtpHost) { this.smtpHost = smtpHost; }
    public void setSmtpPort(int smtpPort)     { this.smtpPort = smtpPort; }

    @Override
    public String getMessage() {
        return "EmailService ready on " + smtpHost + ":" + smtpPort;
    }
}

class SmsMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "SMS Service ready";
    }
}
class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    // Injected via setter (Setter Injection)
    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void sendNotification(String recipient, String content) {
        log.info("Sending notification to: {}", recipient);
        System.out.println("Notification → " + recipient + " | " + content);
        System.out.println("Using: " + messageService.getMessage());
    }
}
