package com.vmware.consumer;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.NonTransientDataAccessResourceException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

import static lombok.AccessLevel.PUBLIC;

@SpringBootApplication
@Slf4j
@FieldDefaults(level = PUBLIC, makeFinal = true)
public class ConsumerApp {

    Counts counts;

    public ConsumerApp(Counts counts) {
        this.counts = counts;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @Bean
    public Consumer<Message<String>> consumer() {
        return msg -> {
            log.info("consumer: msg = {}", msg);

            String payload = msg.getPayload();
            counts.totalCount.incrementAndGet();

            if ("transient".equalsIgnoreCase(payload)) {
                counts.transientCount.incrementAndGet();
                throw new TransientDataAccessResourceException(payload);
            } else if ("nontransient".equalsIgnoreCase(payload)) {
                counts.nonTransientCount.incrementAndGet();
                throw new NonTransientDataAccessResourceException(payload);
            } else {
                counts.successCount.incrementAndGet();
            }
        };
    }

    @Bean
    public Consumer<Message<String>> consumerDlq() {
        return msg -> {
            log.info("consumerDlq: msg = {}", msg);
            counts.dlqCount.incrementAndGet();
        };
    }
}
