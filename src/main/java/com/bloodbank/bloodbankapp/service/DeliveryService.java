package com.bloodbank.bloodbankapp.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.bloodbank.bloodbankapp.dto.NewDeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final List<String> messages = new ArrayList<String>();



    public String getMessage(){
        return messages.get(messages.size() - 1);
    }

    public void StartDelivery(NewDeliveryDto newDelivery) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/start";
        restTemplate.postForObject(url, newDelivery, Object.class);
    }
}
