package com.microservice.stock.listeners;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.microservice.stock.helpers.OrderEventHelper;
import com.microservice.stock.service.StockService;
import com.microservice.stock.util.JsonUtils;

@Configuration
public class KafkaConsumerListener {

    @Autowired
    private StockService stockService;

    private Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @KafkaListener(topics = {"test"}, groupId = "stock-service")
    public void listener(String message){
        OrderEventHelper[] orderDetails0 = JsonUtils.fromJson(message, OrderEventHelper[].class);

        List<OrderEventHelper> orderDetails = Arrays.asList(orderDetails0);

        LOGGER.info("New message received: {}", orderDetails.toString());

        stockService.createProvisionByOrderEvent(orderDetails);
    }

}
