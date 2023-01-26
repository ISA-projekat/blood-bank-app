package com.bloodbank.bloodbankapp.hospitalintegration.monthlytransfer;

import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/monthly-transfer")
@RequiredArgsConstructor
public class MonthlyTransferController {

    @CrossOrigin
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void monthlyTransfer(@RequestBody MonthlyTransfer mt) throws IOException, TimeoutException {
        // Sending monthly reports using

        var factory = new ConnectionFactory();
        factory.setHost("localhost");

        var connection = factory.newConnection();
        var channel = connection.createChannel();

        channel.queueDeclare("monthly", false, false, false, null);

        var message = "A_PLUS:" + mt.APlus + "-" + "A_MINUS:" + mt.AMinus + "-" + "B_PLUS:" + mt.BPlus + "-" + "B_MINUS:" + mt.BMinus + "-" + "AB_PLUS:" + mt.ABPlus + "-" + "AB_MINUS:" + mt.ABMinus + "-" + "O_PLUS:" + mt.OPlus + "-" + "O_MINUS:" + mt.OMinus;

        byte[] body = message.getBytes(StandardCharsets.UTF_8);

        channel.basicPublish("", "monthly", null, body);
    }
}
