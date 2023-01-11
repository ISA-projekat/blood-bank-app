package com.bloodbank.bloodbankapp.hospitalintegration.news;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    @CrossOrigin
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String SendMessage(@RequestBody News news) throws IOException, TimeoutException {
        // Used for sending news using RabbitMQ

        var factory = new ConnectionFactory();
        factory.setHost("localhost");

        var connection = factory.newConnection();
        var channel = connection.createChannel();

        channel.queueDeclare("hello", false, false, false, null);

        byte[] body = NewsService.CreateNewsMessage(news);

        channel.basicPublish("", "hello", null, body);

        return "Message sent";
    }
}
