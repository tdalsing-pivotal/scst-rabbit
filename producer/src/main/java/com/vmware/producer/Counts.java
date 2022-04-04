package com.vmware.producer;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static lombok.AccessLevel.PUBLIC;

@Component
@Slf4j
@FieldDefaults(level = PUBLIC, makeFinal = true)
public class Counts {

    public void clear() {
        totalCount.set(0);
        transientCount.set(0);
        nonTransientCount.set(0);
        successCount.set(0);
        dlqCount.set(0);
    }

    AtomicInteger totalCount = new AtomicInteger(0);
    AtomicInteger transientCount = new AtomicInteger(0);
    AtomicInteger nonTransientCount = new AtomicInteger(0);
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger dlqCount = new AtomicInteger(0);
}
