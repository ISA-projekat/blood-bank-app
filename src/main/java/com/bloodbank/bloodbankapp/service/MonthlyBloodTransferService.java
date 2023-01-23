package com.bloodbank.bloodbankapp.service;

import com.bloodbank.bloodbankapp.model.BloodStock;
import com.bloodbank.bloodbankapp.model.MonthlyBloodTransfer;
import com.bloodbank.bloodbankapp.repository.MonthlyBloodTransferRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class MonthlyBloodTransferService {

    private final MonthlyBloodTransferRepository repository;

    private final BloodStockService bloodStockService;

    private Channel channelFactory() throws Exception {
        var factory = new ConnectionFactory();
        factory.setHost("localhost");

        try{
            var connection = factory.newConnection();
            var channel = connection.createChannel();

            return channel;
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (TimeoutException e) { e.printStackTrace(); }

        throw new Exception("Couldn't create channel");
    }

    private void publishMessage(String queueName, String message) {
        try{
            var channel = channelFactory();
            channel.queueDeclare(queueName, false, false, false, null);
            byte[] body = message.getBytes(StandardCharsets.UTF_8);
            channel.basicPublish("", queueName, null, body);
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (Exception e) { e.printStackTrace(); }
    }

    private void sendBlood(List<MonthlyBloodTransfer> monthlyBloodTransfers) {
        for(MonthlyBloodTransfer monthlyBloodTransfer : monthlyBloodTransfers) {
            if((LocalDateTime.now().getMonth().getValue() == monthlyBloodTransfer.getMonth()) && (LocalDateTime.now().getDayOfMonth() == monthlyBloodTransfer.getDay()) && (monthlyBloodTransfer.isWarned() == false)) {
                BloodStock bloodStock = bloodStockService.findAllByTypeAndRhFactor(monthlyBloodTransfer.getBloodType(), monthlyBloodTransfer.getRhFactor()).get(0);
                bloodStockService.takeBloodStockAmount(bloodStock, monthlyBloodTransfer.getAmount());
                publishMessage(monthlyBloodTransfer.getBloodBankMQName(), "Blood transferred.");
            }
        }
    }

    private void sendWarning(List<MonthlyBloodTransfer> monthlyBloodTransfers) {
        for(MonthlyBloodTransfer monthlyBloodTransfer : monthlyBloodTransfers) {
            BloodStock bloodStock = bloodStockService.findAllByTypeAndRhFactor(monthlyBloodTransfer.getBloodType(), monthlyBloodTransfer.getRhFactor()).get(0);
            LocalDateTime warnTime = LocalDateTime.of(LocalDateTime.now().getYear(), monthlyBloodTransfer.getMonth(), monthlyBloodTransfer.getDay(), 0, 0);
            warnTime = warnTime.minusDays(3);

            if((LocalDateTime.now().getMonth().getValue() == warnTime.getMonth().getValue()) && (LocalDateTime.now().getDayOfMonth() == warnTime.getDayOfMonth()) && (bloodStock.getQuantity() < monthlyBloodTransfer.getAmount())) {
                monthlyBloodTransfer.setWarned(true);
                publishMessage(monthlyBloodTransfer.getBloodBankMQName(), "We do not have the required blood this month");
            }
        }
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void SendMessage() {
        List<MonthlyBloodTransfer> monthlyBloodTransfers = repository.findAll();
        sendBlood(monthlyBloodTransfers);
        sendWarning(monthlyBloodTransfers);
    }

    @Scheduled(cron = "0 0 12 1 * ?")
    public void ResetWarning() {
        List<MonthlyBloodTransfer> monthlyBloodTransfers = repository.findAll();
        for(MonthlyBloodTransfer monthlyBloodTransfer : monthlyBloodTransfers)
            monthlyBloodTransfer.setWarned(false);
    }
}
