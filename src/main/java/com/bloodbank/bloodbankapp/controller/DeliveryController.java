package com.bloodbank.bloodbankapp.controller;

import com.bloodbank.bloodbankapp.dto.NewDeliveryDto;
import com.bloodbank.bloodbankapp.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DeliveryService _deliveryService;

    @PostMapping("/start")
    public String StartDelivery(@RequestBody NewDeliveryDto newDeliveryDto) {
        _deliveryService.StartDelivery(newDeliveryDto);
        return "";
    }

    @RabbitListener(queues = "location")
    public void consumeMessageFromQueue(String orderStatus) {
        System.out.println("Message recieved from queue: " + orderStatus);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher", orderStatus);
    }

}
