package org.ascending.training.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"org.ascending.training.consumer"})
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
//        ConfigurableApplicationContext app = SpringApplication.run(ConsumerApp.class, args);
//        SQSMessageService messageService = app.getBean(SQSMessageService.class);

//        @Autowired
//        SQSMessageService sqsMessageService;
//        messageService.receiveMessage();
    }
}
