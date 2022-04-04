package com.vmware.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@Slf4j
class ProducerAppTest {

    @Autowired
    StreamBridge bridge;

    @Test
    void send_success() {
        Object data = "success";
        bridge.send("out", data, MediaType.TEXT_PLAIN);
    }

    @Test
    void send_transient() {
        Object data = "transient";
        bridge.send("out", data, MediaType.TEXT_PLAIN);
    }

    @Test
    void send_nontransient() {
        Object data = "nontransient";
        bridge.send("out", data, MediaType.TEXT_PLAIN);
    }
}