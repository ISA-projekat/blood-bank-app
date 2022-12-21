package com.bloodbank.bloodbankapp.utils;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Email;

public class MailJetMailer {

    private final static String PublicMailjetKey = "6cdf2011e792898a6181e4e0d8c93b0d";
    private final static String PrivateMailjetKey = "188cd0c4f83511584789aaa4804d6d35";
    public static void SendScheduleAppointmentMail(String recipient) throws MailjetException {

        String template = "<mjml>" +
                "<mj-body>" +
                "<mj-section background-color=\"#ffffff\" padding-top=\"0\">" +
                "<mj-column width=\"500px\">" +
                "<mj-text font-size=\"16px\" align=\"left\">" +
                "<p>You have successfully scheduled your appointment! </p>" +
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
                .property(Email.SUBJECT, "Successful Appointment Scheduling")
                .property(Email.HTMLPART, template)
                .property(Email.TO, recipient);
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
                .property(Email.SUBJECT, "Successful Appointment Scheduling")
                .property(Email.HTMLPART, template)
                .property(Email.TO, recipient);
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }

}
