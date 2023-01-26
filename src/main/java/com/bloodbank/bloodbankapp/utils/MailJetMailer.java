package com.bloodbank.bloodbankapp.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.zxing.WriterException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Service
public class MailJetMailer {

    private final static String PublicMailjetKey = "6cdf2011e792898a6181e4e0d8c93b0d";
    private final static String PrivateMailjetKey = "188cd0c4f83511584789aaa4804d6d35";

    @Autowired
    private  JavaMailSender javaMailSender;

    public static void SendScheduleAppointmentMail(String recipient) throws MailjetException {
        String imagePath = "static/confirmationQR.png";

        try {
            if (!(new File(imagePath).exists())) QRCodeGenerator.generateQRImage();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String template = "<mjml>" +
                "<mj-body>" +
                "<mj-section background-color=\"#ffffff\" padding-top=\"0\">" +
                "<mj-column width=\"500px\">" +
                "<mj-text font-size=\"16px\" align=\"left\">" +
                "<p>You have successfully scheduled your appointment! </p>" +
                "</mj-text>" +
                "<img src='" + imagePath + "' width='200' height='200'/>" +
                "</mj-column>" +
                "</mj-section>" +
                "</mj-body>" +
                "</mjml>";

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(PublicMailjetKey, PrivateMailjetKey);
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "psw.hospital.2022@gmail.com")
                .property(Email.FROMNAME, "Blood Bank Team")
                .property(Email.SUBJECT, "Successful Appointment Scheduling")
                .property(Email.HTMLPART, template)
                .property(Email.TO, recipient);
        System.out.println(request.toString());
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }

    public static void SendActivateAccountMail(String recipient) throws MailjetException {

        String template = "<mjml>" +
                "<mj-body>" +
                "<mj-section background-color=\"#ffffff\" padding-top=\"0\">" +
                "<mj-column width=\"500px\">" +
                "<mj-text font-size=\"16px\" align=\"left\">" +
                "<p>Please activate your account by pressing this <a href=" + "'http://localhost:3000/activate/" + recipient + "'" + ">link<a/> </p>" +
                "</mj-text>" +
                "</mj-column>" +
                "</mj-section>" +
                "</mj-body>" +
                "</mjml>";

        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient(PublicMailjetKey, PrivateMailjetKey);
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, "psw.hospital.2022@gmail.com")
                .property(Email.FROMNAME, "Blood Bank Team")
                .property(Email.SUBJECT, "Account verification")
                .property(Email.HTMLPART, template)
                .property(Email.TO, recipient);

        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }


    public  void sendQRReservation(String toEmail, String body, String subject, String attachment) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        mimeMessageHelper.setFrom("ikiakus@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);

        System.out.println("Mail succesfully sent");
    }


}
