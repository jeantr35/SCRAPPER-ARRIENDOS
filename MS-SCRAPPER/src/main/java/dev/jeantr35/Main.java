package dev.jeantr35;

import dev.jeantr35.infrastructure.consumers.EventTopicExchangeConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException, TimeoutException {
        EventTopicExchangeConsumer eventTopicExchangeConsumer = new EventTopicExchangeConsumer();
        eventTopicExchangeConsumer.setupCityToScrap();
    }
}