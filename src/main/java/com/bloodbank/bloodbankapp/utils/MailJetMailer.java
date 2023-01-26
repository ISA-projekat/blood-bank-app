package com.bloodbank.bloodbankapp.utils;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.google.zxing.WriterException;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Email;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class MailJetMailer {

    private final static String PublicMailjetKey = "6cdf2011e792898a6181e4e0d8c93b0d";
    private final static String PrivateMailjetKey = "188cd0c4f83511584789aaa4804d6d35";

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
                .property(Email.ATTACHMENTS, generateAttachment())
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

    public static JSONArray generateAttachment() {
        try {
            java.nio.file.Path pdfPath = java.nio.file.Paths.get("C:\\FAKS\\Cetvrta godina\\ISA\\Projekat\\blood-bank-app\\static\\confirmationQR.png");
            byte[] filecontent = java.nio.file.Files.readAllBytes(pdfPath);
            String fileData = com.mailjet.client.Base64.encode(filecontent);

            JSONObject obj = new JSONObject().put("Base64Content", fileData).put("ContentType", "image/png")
                    .put("Filename", "confirmation.png");
            JSONArray arr = new JSONArray("["+obj.toString()+"]");
                                                       ;
            System.out.println(arr);
            return arr;
        } catch (Exception e) {
            System.out.println("GRESKA");
            e.printStackTrace();
            return null;
        }
    }
}
